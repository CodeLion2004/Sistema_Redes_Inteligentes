package com.smartgrid.service;

import com.smartgrid.dto.DetalleAlertaZonaDTO;
import com.smartgrid.dto.ReporteAlertasZonaDTO;
import com.smartgrid.model.FacturaEnergetica;
import com.smartgrid.model.Usuario;
import com.smartgrid.patterns.chainofresponsibility.DatosDisponiblesReporteValidator;
import com.smartgrid.patterns.chainofresponsibility.PeriodoReporteValidator;
import com.smartgrid.patterns.chainofresponsibility.ReporteRequest;
import com.smartgrid.patterns.chainofresponsibility.ReporteValidator;
import com.smartgrid.patterns.chainofresponsibility.RolReporteValidator;
import com.smartgrid.patterns.templatemethod.ReporteTemplate;
import com.smartgrid.repository.RepositoryFacturaEnergetica;
import com.smartgrid.repository.RepositoryUsuario;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceReporteAlertasZona
        extends ReporteTemplate<ReporteAlertasZonaDTO, List<FacturaEnergetica>, ServiceReporteAlertasZona.MetricasAlertasZona> {

    private static final double LIMITE_ALERTA_PREVENTIVA = 350.0;
    private static final double LIMITE_ALERTA_CRITICA = 500.0;

    private final RepositoryFacturaEnergetica facturaRepository;
    private final RepositoryUsuario usuarioRepository;

    private String periodo = "semana";
    private String rolUsuario;

    public ServiceReporteAlertasZona(RepositoryFacturaEnergetica facturaRepository,
                                     RepositoryUsuario usuarioRepository) {
        this.facturaRepository = facturaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ReporteAlertasZonaDTO generarReporte(String periodo, String rolUsuario) {
        this.periodo = normalizarPeriodo(periodo);
        this.rolUsuario = rolUsuario;

        validarSolicitud();
        return generarReporte();
    }

    private void validarSolicitud() {
        int cantidadFacturas = (int) facturaRepository.count();

        ReporteValidator cadena = new PeriodoReporteValidator();
        cadena.enlazarCon(new RolReporteValidator())
                .enlazarCon(new DatosDisponiblesReporteValidator(cantidadFacturas));

        cadena.validar(new ReporteRequest(periodo, rolUsuario));
    }

    @Override
    protected List<FacturaEnergetica> obtenerDatos() {
        List<FacturaEnergetica> facturas = facturaRepository.findAll();

        if ("todo".equals(periodo)) {
            return facturas;
        }

        LocalDateTime fechaFin = LocalDateTime.now();
        LocalDateTime fechaInicio = calcularFechaInicio(periodo, fechaFin);

        return facturas.stream()
                .filter(factura -> factura.getFechaEmision() != null)
                .filter(factura -> !factura.getFechaEmision().isBefore(fechaInicio)
                        && !factura.getFechaEmision().isAfter(fechaFin))
                .toList();
    }

    @Override
    protected MetricasAlertasZona procesarDatos(List<FacturaEnergetica> facturas) {
        Map<String, Usuario> usuariosPorId = usuarioRepository.findAll()
                .stream()
                .filter(usuario -> usuario.getId() != null)
                .collect(Collectors.toMap(
                        Usuario::getId,
                        usuario -> usuario,
                        (usuarioExistente, usuarioRepetido) -> usuarioExistente
                ));

        List<FacturaEnergetica> facturasValidas = facturas.stream()
                .filter(factura -> obtenerZonaFactura(factura, usuariosPorId) != null)
                .filter(factura -> esZonaValida(obtenerZonaFactura(factura, usuariosPorId)))
                .toList();

        Map<String, List<FacturaEnergetica>> facturasPorZona = facturasValidas.stream()
                .collect(Collectors.groupingBy(
                        factura -> normalizarTexto(obtenerZonaFactura(factura, usuariosPorId), "SIN_ZONA"),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<DetalleAlertaZonaDTO> detalleZonas = new ArrayList<>();

        for (Map.Entry<String, List<FacturaEnergetica>> entry : facturasPorZona.entrySet()) {
            String zona = entry.getKey();
            List<FacturaEnergetica> facturasZona = entry.getValue();

            int totalUsuarios = contarUsuariosUnicos(facturasZona);
            int totalConsumos = facturasZona.size();

            double consumoTotal = redondear(
                    facturasZona.stream()
                            .mapToDouble(this::obtenerConsumoFactura)
                            .sum()
            );

            double consumoPromedio = totalConsumos > 0
                    ? redondear(consumoTotal / totalConsumos)
                    : 0.0;

            int alertasCriticas = (int) facturasZona.stream()
                    .filter(factura -> obtenerConsumoFactura(factura) >= LIMITE_ALERTA_CRITICA)
                    .count();

            int alertasPreventivas = (int) facturasZona.stream()
                    .filter(factura -> obtenerConsumoFactura(factura) >= LIMITE_ALERTA_PREVENTIVA
                            && obtenerConsumoFactura(factura) < LIMITE_ALERTA_CRITICA)
                    .count();

            int totalAlertas = alertasPreventivas + alertasCriticas;

            if (totalAlertas == 0) {
                continue;
            }

            String nivelRiesgo = calcularNivelRiesgo(totalAlertas, alertasCriticas, consumoPromedio);
            String recomendacion = generarRecomendacionZona(zona, nivelRiesgo);

            detalleZonas.add(new DetalleAlertaZonaDTO(
                    zona,
                    totalUsuarios,
                    totalConsumos,
                    consumoTotal,
                    consumoPromedio,
                    alertasPreventivas,
                    alertasCriticas,
                    totalAlertas,
                    nivelRiesgo,
                    recomendacion
            ));
        }

        detalleZonas.sort(
                Comparator.comparing(DetalleAlertaZonaDTO::getTotalAlertas).reversed()
                        .thenComparing(DetalleAlertaZonaDTO::getAlertasCriticas, Comparator.reverseOrder())
                        .thenComparing(DetalleAlertaZonaDTO::getConsumoPromedio, Comparator.reverseOrder())
        );

        int totalAlertasPreventivas = detalleZonas.stream()
                .mapToInt(DetalleAlertaZonaDTO::getAlertasPreventivas)
                .sum();

        int totalAlertasCriticas = detalleZonas.stream()
                .mapToInt(DetalleAlertaZonaDTO::getAlertasCriticas)
                .sum();

        int totalAlertas = totalAlertasPreventivas + totalAlertasCriticas;

        String zonaMayorRiesgo = detalleZonas.stream()
                .findFirst()
                .map(DetalleAlertaZonaDTO::getZona)
                .orElse("SIN_DATOS");

        return new MetricasAlertasZona(
                detalleZonas.size(),
                totalAlertas,
                totalAlertasPreventivas,
                totalAlertasCriticas,
                zonaMayorRiesgo,
                detalleZonas
        );
    }

    @Override
    protected ReporteAlertasZonaDTO construirRespuesta(MetricasAlertasZona metricas) {
        return new ReporteAlertasZonaDTO(
                metricas.totalZonasAnalizadas(),
                metricas.totalAlertas(),
                metricas.totalAlertasPreventivas(),
                metricas.totalAlertasCriticas(),
                metricas.zonaMayorRiesgo(),
                metricas.zonas(),
                generarRecomendaciones(metricas)
        );
    }

    private String obtenerZonaFactura(FacturaEnergetica factura, Map<String, Usuario> usuariosPorId) {
        if (factura.getZona() != null && !factura.getZona().isBlank()) {
            return factura.getZona();
        }

        if (factura.getIdUsuario() == null || factura.getIdUsuario().isBlank()) {
            return null;
        }

        Usuario usuario = usuariosPorId.get(factura.getIdUsuario());

        if (usuario == null) {
            return null;
        }

        return usuario.getZona();
    }

    private double obtenerConsumoFactura(FacturaEnergetica factura) {
        return factura.getConsumo();
    }

    private int contarUsuariosUnicos(List<FacturaEnergetica> facturas) {
        return (int) facturas.stream()
                .map(factura -> {
                    if (factura.getIdUsuario() != null && !factura.getIdUsuario().isBlank()) {
                        return factura.getIdUsuario();
                    }

                    if (factura.getNombreUsuario() != null && !factura.getNombreUsuario().isBlank()) {
                        return factura.getNombreUsuario();
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .distinct()
                .count();
    }

    private boolean esZonaValida(String zona) {
        if (zona == null || zona.isBlank()) {
            return false;
        }

        String zonaNormalizada = zona.trim().toUpperCase(Locale.ROOT);

        return !zonaNormalizada.equals("SIN_USUARIO")
                && !zonaNormalizada.equals("USUARIO_NO_ENCONTRADO")
                && !zonaNormalizada.equals("SIN_ZONA");
    }

    private String calcularNivelRiesgo(int totalAlertas, int alertasCriticas, double consumoPromedio) {
        if (alertasCriticas >= 3 || consumoPromedio >= LIMITE_ALERTA_CRITICA) {
            return "CRITICO";
        }

        if (alertasCriticas > 0 || totalAlertas >= 3 || consumoPromedio >= LIMITE_ALERTA_PREVENTIVA) {
            return "ALTO";
        }

        if (totalAlertas > 0) {
            return "MEDIO";
        }

        return "BAJO";
    }

    private String generarRecomendacionZona(String zona, String nivelRiesgo) {
        return switch (nivelRiesgo) {
            case "CRITICO" -> "La zona " + zona + " requiere intervención inmediata por consumo crítico.";
            case "ALTO" -> "La zona " + zona + " debe ser monitoreada por acumulación de alertas.";
            case "MEDIO" -> "La zona " + zona + " presenta señales preventivas de consumo elevado.";
            default -> "La zona " + zona + " mantiene un comportamiento estable.";
        };
    }

    private List<String> generarRecomendaciones(MetricasAlertasZona metricas) {
        List<String> recomendaciones = new ArrayList<>();

        if (metricas.totalZonasAnalizadas() == 0) {
            recomendaciones.add("No se detectaron zonas con alertas en el periodo seleccionado.");
            recomendaciones.add("El reporte ahora analiza facturas asociadas a usuarios y zonas válidas.");
            return recomendaciones;
        }

        recomendaciones.add(
                "La zona con mayor riesgo operativo es " + metricas.zonaMayorRiesgo() + "."
        );

        if (metricas.totalAlertasCriticas() > 0) {
            recomendaciones.add(
                    "Existen " + metricas.totalAlertasCriticas() +
                            " alertas críticas. Se recomienda priorizar acciones correctivas."
            );
        }

        if (metricas.totalAlertasPreventivas() > metricas.totalAlertasCriticas()) {
            recomendaciones.add(
                    "Predominan las alertas preventivas; es buen momento para actuar antes de llegar a niveles críticos."
            );
        }

        recomendaciones.add(
                "Este reporte trabaja sobre el flujo de facturación/usuarios, no sobre consumos externos."
        );

        recomendaciones.add(
                "Los consumos externos se administran de forma independiente desde Registro de Consumo."
        );

        return recomendaciones;
    }

    private String normalizarPeriodo(String periodo) {
        if (periodo == null || periodo.isBlank()) {
            return "semana";
        }

        String valor = periodo.toLowerCase(Locale.ROOT).trim();

        return switch (valor) {
            case "dia", "hoy" -> "dia";
            case "semana", "7dias", "ultimos7dias" -> "semana";
            case "mes", "30dias", "ultimos30dias" -> "mes";
            case "anio", "año", "365dias" -> "anio";
            case "todo", "historico", "histórico" -> "todo";
            default -> valor;
        };
    }

    private LocalDateTime calcularFechaInicio(String periodo, LocalDateTime fechaFin) {
        LocalDate hoy = fechaFin.toLocalDate();

        return switch (periodo) {
            case "dia" -> hoy.atStartOfDay();
            case "semana" -> hoy.minusDays(6).atStartOfDay();
            case "mes" -> hoy.minusDays(29).atStartOfDay();
            case "anio" -> hoy.minusDays(364).atStartOfDay();
            default -> LocalDate.of(1970, 1, 1).atStartOfDay();
        };
    }

    private String normalizarTexto(String valor, String defecto) {
        if (valor == null || valor.isBlank()) {
            return defecto;
        }

        return valor.trim().toUpperCase(Locale.ROOT);
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public record MetricasAlertasZona(
            int totalZonasAnalizadas,
            int totalAlertas,
            int totalAlertasPreventivas,
            int totalAlertasCriticas,
            String zonaMayorRiesgo,
            List<DetalleAlertaZonaDTO> zonas
    ) {
    }
}