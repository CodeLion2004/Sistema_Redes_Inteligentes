package com.smartgrid.service;

import com.smartgrid.dto.DetalleFuenteEnergiaDTO;
import com.smartgrid.dto.ReporteFuenteEnergiaDTO;
import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.patterns.chainofresponsibility.DatosDisponiblesReporteValidator;
import com.smartgrid.patterns.chainofresponsibility.PeriodoReporteValidator;
import com.smartgrid.patterns.chainofresponsibility.ReporteRequest;
import com.smartgrid.patterns.chainofresponsibility.ReporteValidator;
import com.smartgrid.patterns.chainofresponsibility.RolReporteValidator;
import com.smartgrid.patterns.templatemethod.ReporteTemplate;
import com.smartgrid.repository.RepositoryConsumoEnergetico;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceReporteFuenteEnergia
        extends ReporteTemplate<ReporteFuenteEnergiaDTO,
                                List<ConsumoEnergetico>,
                                ServiceReporteFuenteEnergia.MetricasFuenteEnergia> {

    private final RepositoryConsumoEnergetico consumoRepository;

    private String periodo    = "semana";
    private String rolUsuario = "ADMIN";

    public ServiceReporteFuenteEnergia(RepositoryConsumoEnergetico consumoRepository) {
        this.consumoRepository = consumoRepository;
    }


    public ReporteFuenteEnergiaDTO generarReporte(String periodo, String rolUsuario) {
        this.periodo    = normalizarPeriodo(periodo);
        this.rolUsuario = rolUsuario;

        validarSolicitud();
        return generarReporte();   // llama al método final de ReporteTemplate
    }


    private void validarSolicitud() {
        int cantidad = (int) consumoRepository.count();

        ReporteValidator cadena = new PeriodoReporteValidator();
        cadena.enlazarCon(new RolReporteValidator())
              .enlazarCon(new DatosDisponiblesReporteValidator(cantidad));

        cadena.validar(new ReporteRequest(periodo, rolUsuario));
    }


    @Override
    protected List<ConsumoEnergetico> obtenerDatos() {
        if ("todo".equals(periodo)) {
            return consumoRepository.findAll();
        }

        LocalDateTime fechaFin    = LocalDateTime.now();
        LocalDateTime fechaInicio = calcularFechaInicio(periodo, fechaFin);

        return consumoRepository.findByMarcaTiempoBetween(fechaInicio, fechaFin);
    }

    /* ════════════════════════════════════════════
       PASO 2 — procesarDatos()
       Agrupa por fuente y calcula métricas
       ════════════════════════════════════════════ */
    @Override
    protected MetricasFuenteEnergia procesarDatos(List<ConsumoEnergetico> registros) {

        // Agrupar por fuente de energía
        Map<String, List<ConsumoEnergetico>> porFuente = registros.stream()
                .collect(Collectors.groupingBy(
                        c -> normalizarTexto(c.getFuenteEnergia(), "NO_ESPECIFICADA"),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        int    totalRegistros = registros.size();
        double consumoTotal   = redondear(
                registros.stream().mapToDouble(ConsumoEnergetico::getConsumo).sum()
        );
        double consumoPromedio = totalRegistros > 0
                ? redondear(consumoTotal / totalRegistros)
                : 0.0;

        List<DetalleFuenteEnergiaDTO> detalleFuentes = new ArrayList<>();
        Map<String, Double> consumoPorFuente         = new LinkedHashMap<>();

        for (Map.Entry<String, List<ConsumoEnergetico>> entry : porFuente.entrySet()) {
            String fuente     = entry.getKey();
            List<ConsumoEnergetico> grupo = entry.getValue();

            int    cantidad  = grupo.size();
            double total     = redondear(grupo.stream().mapToDouble(ConsumoEnergetico::getConsumo).sum());
            double promedio  = cantidad > 0 ? redondear(total / cantidad) : 0.0;
            double porcentaje = consumoTotal > 0
                    ? redondear((total * 100.0) / consumoTotal)
                    : 0.0;

            detalleFuentes.add(new DetalleFuenteEnergiaDTO(fuente, cantidad, total, promedio, porcentaje));
            consumoPorFuente.put(fuente, total);
        }

        // Ordenar de mayor a menor consumo
        detalleFuentes.sort(Comparator.comparing(DetalleFuenteEnergiaDTO::getConsumoTotal).reversed());

        String fuenteDominante = detalleFuentes.stream()
                .findFirst()
                .map(DetalleFuenteEnergiaDTO::getFuente)
                .orElse("SIN_DATOS");

        return new MetricasFuenteEnergia(
                totalRegistros,
                consumoTotal,
                consumoPromedio,
                fuenteDominante,
                detalleFuentes,
                consumoPorFuente
        );
    }

    /* ════════════════════════════════════════════
       PASO 3 — construirRespuesta()
       Ensambla el DTO final con recomendaciones
       ════════════════════════════════════════════ */
    @Override
    protected ReporteFuenteEnergiaDTO construirRespuesta(MetricasFuenteEnergia metricas) {
        return new ReporteFuenteEnergiaDTO(
                metricas.totalRegistros(),
                metricas.consumoTotal(),
                metricas.consumoPromedio(),
                metricas.fuenteDominante(),
                metricas.detalleFuentes(),
                metricas.consumoPorFuente(),
                generarRecomendaciones(metricas)
        );
    }

    /* ── Recomendaciones inteligentes ── */
    private List<String> generarRecomendaciones(MetricasFuenteEnergia m) {
        List<String> rec = new ArrayList<>();

        if (m.totalRegistros() == 0) {
            rec.add("No se encontraron registros de consumo para el periodo seleccionado.");
            return rec;
        }

        rec.add("La fuente dominante es " + m.fuenteDominante()
                + ", que concentra el mayor volumen de consumo del periodo.");

        double consumoRenovable = m.consumoPorFuente().entrySet().stream()
                .filter(e -> e.getKey().contains("SOLAR") || e.getKey().contains("EOLICA"))
                .mapToDouble(Map.Entry::getValue)
                .sum();

        double porcentajeRenovable = m.consumoTotal() > 0
                ? redondear((consumoRenovable * 100.0) / m.consumoTotal())
                : 0.0;

        if (porcentajeRenovable >= 50) {
            rec.add("El " + porcentajeRenovable + "% del consumo proviene de fuentes renovables. Excelente desempeño sostenible.");
        } else if (porcentajeRenovable > 0) {
            rec.add("Solo el " + porcentajeRenovable + "% proviene de fuentes renovables. Se recomienda incrementar el uso de SOLAR o EOLICA.");
        } else {
            rec.add("No se detectó consumo de fuentes renovables en el periodo. Considere integrar energía solar o eólica.");
        }

        if (m.consumoPorFuente().containsKey("RED")) {
            double consumoRed = m.consumoPorFuente().get("RED");
            double pctRed = m.consumoTotal() > 0 ? redondear((consumoRed * 100.0) / m.consumoTotal()) : 0.0;
            if (pctRed > 60) {
                rec.add("El " + pctRed + "% del consumo depende de la red eléctrica convencional. Alta dependencia de fuente no renovable.");
            }
        }

        if (m.detalleFuentes().size() >= 3) {
            rec.add("Se detectaron " + m.detalleFuentes().size() + " fuentes distintas. Una mayor diversificación mejora la resiliencia de la red.");
        }

        return rec;
    }

    /* ── Utilidades ── */
    private String normalizarPeriodo(String p) {
        if (p == null || p.isBlank()) return "semana";
        return switch (p.toLowerCase(Locale.ROOT).trim()) {
            case "dia", "hoy"                              -> "dia";
            case "semana", "7dias", "ultimos7dias"         -> "semana";
            case "mes", "30dias", "ultimos30dias"          -> "mes";
            case "anio", "año", "365dias"                  -> "anio";
            case "todo", "historico", "histórico"          -> "todo";
            default -> p.toLowerCase(Locale.ROOT).trim();
        };
    }

    private LocalDateTime calcularFechaInicio(String periodo, LocalDateTime fechaFin) {
        LocalDate hoy = fechaFin.toLocalDate();
        return switch (periodo) {
            case "dia"   -> hoy.atStartOfDay();
            case "semana"-> hoy.minusDays(6).atStartOfDay();
            case "mes"   -> hoy.minusDays(29).atStartOfDay();
            case "anio"  -> hoy.minusDays(364).atStartOfDay();
            default      -> LocalDate.of(1970, 1, 1).atStartOfDay();
        };
    }

    private String normalizarTexto(String valor, String defecto) {
        if (valor == null || valor.isBlank()) return defecto;
        return valor.trim().toUpperCase(Locale.ROOT);
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    /* ── Record interno de métricas ── */
    public record MetricasFuenteEnergia(
            int totalRegistros,
            double consumoTotal,
            double consumoPromedio,
            String fuenteDominante,
            List<DetalleFuenteEnergiaDTO> detalleFuentes,
            Map<String, Double> consumoPorFuente
    ) {}
}