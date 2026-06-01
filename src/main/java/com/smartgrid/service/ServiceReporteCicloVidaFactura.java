package com.smartgrid.service;

import com.smartgrid.dto.DetalleFacturaCicloVidaDTO;
import com.smartgrid.dto.ReporteCicloVidaFacturaDTO;
import com.smartgrid.model.FacturaEnergetica;
import com.smartgrid.patterns.chainofresponsibility.DatosDisponiblesReporteValidator;
import com.smartgrid.patterns.chainofresponsibility.PeriodoReporteValidator;
import com.smartgrid.patterns.chainofresponsibility.ReporteRequest;
import com.smartgrid.patterns.chainofresponsibility.ReporteValidator;
import com.smartgrid.patterns.chainofresponsibility.RolReporteValidator;
import com.smartgrid.patterns.templatemethod.ReporteTemplate;
import com.smartgrid.repository.RepositoryFacturaEnergetica;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceReporteCicloVidaFactura
        extends ReporteTemplate<ReporteCicloVidaFacturaDTO,
                                List<FacturaEnergetica>,
                                ServiceReporteCicloVidaFactura.MetricasCicloVida> {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Transiciones válidas / bloqueadas por estado — refleja exactamente el patrón State
    private static final Map<String, List<String>> TRANSICIONES_PERMITIDAS = Map.of(
            "PENDIENTE", List.of("PAGAR", "VENCER", "ANULAR"),
            "VENCIDA",   List.of("PAGAR", "ANULAR"),
            "PAGADA",    List.of(),
            "ANULADA",   List.of()
    );

    private static final Map<String, List<String>> TRANSICIONES_BLOQUEADAS = Map.of(
            "PENDIENTE", List.of(),
            "VENCIDA",   List.of("VENCER"),
            "PAGADA",    List.of("PAGAR", "VENCER", "ANULAR"),
            "ANULADA",   List.of("PAGAR", "VENCER", "ANULAR")
    );

    private final RepositoryFacturaEnergetica facturaRepository;

    private String periodo    = "semana";
    private String rolUsuario = "ADMIN";

    public ServiceReporteCicloVidaFactura(RepositoryFacturaEnergetica facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    /* ── Punto de entrada público ── */
    public ReporteCicloVidaFacturaDTO generarReporte(String periodo, String rolUsuario) {
        this.periodo    = normalizarPeriodo(periodo);
        this.rolUsuario = rolUsuario;
        validarSolicitud();
        return generarReporte();
    }

    /* ── Validación con Chain of Responsibility ── */
    private void validarSolicitud() {
        int cantidad = (int) facturaRepository.count();
        ReporteValidator cadena = new PeriodoReporteValidator();
        cadena.enlazarCon(new RolReporteValidator())
              .enlazarCon(new DatosDisponiblesReporteValidator(cantidad));
        cadena.validar(new ReporteRequest(periodo, rolUsuario));
    }

    /* ════════════════════════════════════════════
       PASO 1 — obtenerDatos()
       Trae facturas de MongoDB filtradas por período
       ════════════════════════════════════════════ */
    @Override
    protected List<FacturaEnergetica> obtenerDatos() {
        List<FacturaEnergetica> todas = facturaRepository.findAll();

        if ("todo".equals(periodo)) return todas;

        LocalDateTime fechaFin    = LocalDateTime.now();
        LocalDateTime fechaInicio = calcularFechaInicio(periodo, fechaFin);

        return todas.stream()
                .filter(f -> f.getFechaEmision() != null)
                .filter(f -> !f.getFechaEmision().isBefore(fechaInicio)
                          && !f.getFechaEmision().isAfter(fechaFin))
                .toList();
    }

    /* ════════════════════════════════════════════
       PASO 2 — procesarDatos()
       Agrupa por estado y calcula métricas del ciclo de vida
       ════════════════════════════════════════════ */
    @Override
    protected MetricasCicloVida procesarDatos(List<FacturaEnergetica> facturas) {

        int totalFacturas = facturas.size();

        double montoTotal = redondear(
                facturas.stream().mapToDouble(FacturaEnergetica::getTotal).sum());

        // Conteo y monto por estado
        Map<String, Integer> conteoPorEstado = new LinkedHashMap<>();
        Map<String, Double>  montoPorEstado  = new LinkedHashMap<>();

        // Inicializar con los 4 estados para que siempre aparezcan en la respuesta
        for (String estado : List.of("PENDIENTE", "VENCIDA", "PAGADA", "ANULADA")) {
            conteoPorEstado.put(estado, 0);
            montoPorEstado.put(estado, 0.0);
        }

        for (FacturaEnergetica f : facturas) {
            String estado = normalizarEstado(f.getEstado());
            conteoPorEstado.merge(estado, 1, Integer::sum);
            montoPorEstado.merge(estado, f.getTotal(), Double::sum);
        }

        montoPorEstado.replaceAll((k, v) -> redondear(v));

        double montoRecuperable = montoPorEstado.getOrDefault("VENCIDA", 0.0);
        double montoBloqueado   = montoPorEstado.getOrDefault("ANULADA", 0.0);

        // Estado dominante por cantidad de facturas
        String estadoDominante = conteoPorEstado.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("SIN_DATOS");

        // Detalle individual con transiciones por Estado (patrón State)
        List<DetalleFacturaCicloVidaDTO> detalle = facturas.stream()
                .map(f -> {
                    String estado = normalizarEstado(f.getEstado());
                    return new DetalleFacturaCicloVidaDTO(
                            f.getId(),
                            f.getNombreUsuario() != null ? f.getNombreUsuario() : "Sin nombre",
                            f.getZona() != null ? f.getZona() : "Sin zona",
                            redondear(f.getTotal()),
                            estado,
                            f.getFechaEmision() != null
                                    ? f.getFechaEmision().format(FMT)
                                    : "Sin fecha",
                            TRANSICIONES_PERMITIDAS.getOrDefault(estado, List.of()),
                            TRANSICIONES_BLOQUEADAS.getOrDefault(estado, List.of())
                    );
                })
                .sorted(Comparator.comparing(DetalleFacturaCicloVidaDTO::getEstado))
                .toList();

        return new MetricasCicloVida(
                totalFacturas,
                montoTotal,
                montoRecuperable,
                montoBloqueado,
                estadoDominante,
                conteoPorEstado,
                montoPorEstado,
                detalle
        );
    }

    /* ════════════════════════════════════════════
       PASO 3 — construirRespuesta()
       Ensambla el DTO final con recomendaciones
       ════════════════════════════════════════════ */
    @Override
    protected ReporteCicloVidaFacturaDTO construirRespuesta(MetricasCicloVida m) {
        return new ReporteCicloVidaFacturaDTO(
                m.totalFacturas(),
                m.montoTotal(),
                m.montoRecuperable(),
                m.montoBloqueado(),
                m.estadoDominante(),
                m.conteoPorEstado(),
                m.montoPorEstado(),
                m.detalle(),
                generarRecomendaciones(m)
        );
    }

    /* ── Recomendaciones basadas en el ciclo de vida ── */
    private List<String> generarRecomendaciones(MetricasCicloVida m) {
        List<String> rec = new ArrayList<>();

        if (m.totalFacturas() == 0) {
            rec.add("No se encontraron facturas para el período seleccionado.");
            return rec;
        }

        int pendientes = m.conteoPorEstado().getOrDefault("PENDIENTE", 0);
        int vencidas   = m.conteoPorEstado().getOrDefault("VENCIDA", 0);
        int anuladas   = m.conteoPorEstado().getOrDefault("ANULADA", 0);
        int pagadas    = m.conteoPorEstado().getOrDefault("PAGADA", 0);

        if (vencidas > 0) {
            rec.add("Hay " + vencidas + " factura(s) VENCIDA(S) por $" + m.montoRecuperable()
                    + ". El patrón State permite recuperarlas ejecutando la acción PAGAR o ANULAR.");
        }

        if (anuladas > 0) {
            rec.add("$" + m.montoBloqueado() + " están en facturas ANULADAS. "
                    + "El estado EstadoAnulada bloquea todas las transiciones — monto irrecuperable.");
        }

        double pctPendiente = m.totalFacturas() > 0
                ? redondear((pendientes * 100.0) / m.totalFacturas()) : 0;
        if (pctPendiente > 40) {
            rec.add("El " + pctPendiente + "% de las facturas están PENDIENTES. "
                    + "Se recomienda ejecutar transiciones de estado antes del vencimiento.");
        }

        if (pagadas > 0) {
            rec.add("Las facturas en estado PAGADA tienen todas las transiciones bloqueadas por "
                    + "EstadoPagada — estado terminal sin retorno, garantizando integridad del cobro.");
        }

        rec.add("El patrón State asegura que cada transición sea válida según el estado actual: "
                + "PENDIENTE acepta 3 acciones, VENCIDA acepta 2, PAGADA y ANULADA son estados terminales.");

        return rec;
    }

    /* ── Utilidades ── */
    private String normalizarEstado(String estado) {
        if (estado == null || estado.isBlank()) return "PENDIENTE";
        return switch (estado.trim().toUpperCase(Locale.ROOT)) {
            case "PAGADA"    -> "PAGADA";
            case "VENCIDA"   -> "VENCIDA";
            case "ANULADA"   -> "ANULADA";
            default          -> "PENDIENTE";
        };
    }

    private String normalizarPeriodo(String p) {
        if (p == null || p.isBlank()) return "semana";
        return switch (p.toLowerCase(Locale.ROOT).trim()) {
            case "dia", "hoy"                          -> "dia";
            case "semana", "7dias", "ultimos7dias"     -> "semana";
            case "mes", "30dias", "ultimos30dias"      -> "mes";
            case "anio", "año", "365dias"              -> "anio";
            case "todo", "historico", "histórico"      -> "todo";
            default -> p.toLowerCase(Locale.ROOT).trim();
        };
    }

    private LocalDateTime calcularFechaInicio(String periodo, LocalDateTime fechaFin) {
        LocalDate hoy = fechaFin.toLocalDate();
        return switch (periodo) {
            case "dia"    -> hoy.atStartOfDay();
            case "semana" -> hoy.minusDays(6).atStartOfDay();
            case "mes"    -> hoy.minusDays(29).atStartOfDay();
            case "anio"   -> hoy.minusDays(364).atStartOfDay();
            default       -> LocalDate.of(1970, 1, 1).atStartOfDay();
        };
    }

    private double redondear(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    /* ── Record interno de métricas ── */
    public record MetricasCicloVida(
            int totalFacturas,
            double montoTotal,
            double montoRecuperable,
            double montoBloqueado,
            String estadoDominante,
            Map<String, Integer> conteoPorEstado,
            Map<String, Double>  montoPorEstado,
            List<DetalleFacturaCicloVidaDTO> detalle
    ) {}
}