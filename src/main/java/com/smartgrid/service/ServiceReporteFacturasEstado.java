package com.smartgrid.service;

import com.smartgrid.dto.DetalleEstadoFacturaDTO;
import com.smartgrid.dto.ReporteFacturasEstadoDTO;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceReporteFacturasEstado
        extends ReporteTemplate<ReporteFacturasEstadoDTO, List<FacturaEnergetica>, ServiceReporteFacturasEstado.MetricasFacturasEstado> {

    private final RepositoryFacturaEnergetica facturaRepository;
    private String periodo = "semana";
    private String rolUsuario;

    public ServiceReporteFacturasEstado(RepositoryFacturaEnergetica facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public ReporteFacturasEstadoDTO generarReporte(String periodo, String rolUsuario) {
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
    protected MetricasFacturasEstado procesarDatos(List<FacturaEnergetica> facturas) {
        Map<String, List<FacturaEnergetica>> facturasPorEstado = facturas.stream()
                .collect(Collectors.groupingBy(
                        factura -> normalizarTexto(factura.getEstado(), "SIN_ESTADO"),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        int totalFacturas = facturas.size();

        double totalFacturado = redondear(
                facturas.stream()
                        .mapToDouble(FacturaEnergetica::getTotal)
                        .sum()
        );

        List<DetalleEstadoFacturaDTO> detalleEstados = new ArrayList<>();
        Map<String, Double> totalPorEstado = new LinkedHashMap<>();

        for (Map.Entry<String, List<FacturaEnergetica>> entry : facturasPorEstado.entrySet()) {
            String estado = entry.getKey();
            List<FacturaEnergetica> facturasEstado = entry.getValue();

            int cantidad = facturasEstado.size();

            double totalEstado = redondear(
                    facturasEstado.stream()
                            .mapToDouble(FacturaEnergetica::getTotal)
                            .sum()
            );

            double porcentajeFacturas = totalFacturas > 0
                    ? redondear((cantidad * 100.0) / totalFacturas)
                    : 0.0;

            double porcentajeIngresos = totalFacturado > 0
                    ? redondear((totalEstado * 100.0) / totalFacturado)
                    : 0.0;

            detalleEstados.add(new DetalleEstadoFacturaDTO(
                    estado,
                    cantidad,
                    totalEstado,
                    porcentajeFacturas,
                    porcentajeIngresos
            ));

            totalPorEstado.put(estado, totalEstado);
        }

        detalleEstados.sort(
                Comparator.comparing(DetalleEstadoFacturaDTO::getTotalFacturado).reversed()
        );

        String estadoDominante = detalleEstados.stream()
                .findFirst()
                .map(DetalleEstadoFacturaDTO::getEstado)
                .orElse("SIN_DATOS");

        return new MetricasFacturasEstado(
                totalFacturas,
                totalFacturado,
                estadoDominante,
                detalleEstados,
                totalPorEstado
        );
    }

    @Override
    protected ReporteFacturasEstadoDTO construirRespuesta(MetricasFacturasEstado metricas) {
        return new ReporteFacturasEstadoDTO(
                metricas.totalFacturas(),
                metricas.totalFacturado(),
                metricas.estadoDominante(),
                metricas.detalleEstados(),
                metricas.totalPorEstado(),
                generarRecomendaciones(metricas)
        );
    }

    private List<String> generarRecomendaciones(MetricasFacturasEstado metricas) {
        List<String> recomendaciones = new ArrayList<>();

        if (metricas.totalFacturas() == 0) {
            recomendaciones.add("No hay facturas en el periodo seleccionado.");
            return recomendaciones;
        }

        recomendaciones.add(
                "El estado con mayor peso económico es " + metricas.estadoDominante() + "."
        );

        double totalVencido = metricas.totalPorEstado().getOrDefault("VENCIDA", 0.0);

        if (totalVencido > 0) {
            recomendaciones.add(
                    "Existen facturas vencidas por $" + totalVencido +
                            ". Se recomienda priorizar gestión de cobro."
            );
        }

        double totalPendiente = metricas.totalPorEstado().getOrDefault("PENDIENTE", 0.0);

        if (totalPendiente > metricas.totalFacturado() * 0.3) {
            recomendaciones.add(
                    "Las facturas pendientes representan una proporción alta del total facturado."
            );
        }

        if (metricas.totalPorEstado().containsKey("PAGADA")) {
            recomendaciones.add(
                    "Se puede comparar el total pagado contra pendiente para medir eficiencia de recaudo."
            );
        }

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

    public record MetricasFacturasEstado(
            int totalFacturas,
            double totalFacturado,
            String estadoDominante,
            List<DetalleEstadoFacturaDTO> detalleEstados,
            Map<String, Double> totalPorEstado
    ) {
    }
}