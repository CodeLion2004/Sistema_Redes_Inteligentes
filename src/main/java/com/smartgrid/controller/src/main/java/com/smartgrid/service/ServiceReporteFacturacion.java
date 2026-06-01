package com.smartgrid.service;

import com.smartgrid.dto.ReporteFacturacionDTO;
import com.smartgrid.model.FacturaEnergetica;
import com.smartgrid.model.Usuario;
import com.smartgrid.repository.RepositoryFacturaEnergetica;
import com.smartgrid.repository.RepositoryUsuario;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ServiceReporteFacturacion {

    private final RepositoryFacturaEnergetica facturaRepository;
    private final RepositoryUsuario usuarioRepository;

    public ServiceReporteFacturacion(
            RepositoryFacturaEnergetica facturaRepository,
            RepositoryUsuario usuarioRepository
    ) {
        this.facturaRepository = facturaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ReporteFacturacionDTO generarReporte(String periodo) {

        String periodoNormalizado = normalizarPeriodo(periodo);

        LocalDateTime fechaFin = LocalDateTime.now();
        LocalDateTime fechaInicio = calcularFechaInicio(periodoNormalizado, fechaFin);

        List<FacturaEnergetica> facturas = facturaRepository.findAll();

        if (!periodoNormalizado.equals("todo")) {
            facturas = facturas.stream()
                    .filter(f -> f.getFechaEmision() != null)
                    .filter(f -> !f.getFechaEmision().isBefore(fechaInicio)
                            && !f.getFechaEmision().isAfter(fechaFin))
                    .toList();
        }

        Map<String, String> nombresUsuarios = obtenerMapaUsuarios();

        Map<String, Double> ingresosPorUsuario = new LinkedHashMap<>();
        Map<String, Double> ingresosPorFuente = new LinkedHashMap<>();
        Map<String, Double> ingresosPorTipoFacturacion = new LinkedHashMap<>();

        double ingresoTotal = 0;
        double subtotalTotal = 0;
        double impuestosTotal = 0;
        double descuentosTotal = 0;

        for (FacturaEnergetica factura : facturas) {

            double total = factura.getTotal();
            double subtotal = factura.getSubtotal();
            double impuestos = factura.getImpuestos();
            double descuento = factura.getDescuento();

            ingresoTotal += total;
            subtotalTotal += subtotal;
            impuestosTotal += impuestos;
            descuentosTotal += descuento;

            String nombreUsuario = nombresUsuarios.getOrDefault(
                    factura.getIdUsuario(),
                    "Usuario no encontrado"
            );

            String fuente = normalizarTexto(factura.getFuenteEnergia(), "Sin fuente");
            String tipo = normalizarTexto(factura.getTipoFacturacion(), "Sin tipo");

            ingresosPorUsuario.merge(nombreUsuario, total, Double::sum);
            ingresosPorFuente.merge(fuente, total, Double::sum);
            ingresosPorTipoFacturacion.merge(tipo, total, Double::sum);
        }

        ingresosPorUsuario.replaceAll((k, v) -> redondear(v));
        ingresosPorFuente.replaceAll((k, v) -> redondear(v));
        ingresosPorTipoFacturacion.replaceAll((k, v) -> redondear(v));

        ingresoTotal = redondear(ingresoTotal);
        subtotalTotal = redondear(subtotalTotal);
        impuestosTotal = redondear(impuestosTotal);
        descuentosTotal = redondear(descuentosTotal);

        int totalFacturas = facturas.size();

        double promedioFactura = totalFacturas > 0
                ? redondear(ingresoTotal / totalFacturas)
                : 0.0;

        String usuarioMayorFacturacion = obtenerClaveMayorValor(
                ingresosPorUsuario,
                "Sin datos"
        );

        double mayorFacturacionUsuario = ingresosPorUsuario.getOrDefault(
                usuarioMayorFacturacion,
                0.0
        );

        String fuenteMasRentable = obtenerClaveMayorValor(
                ingresosPorFuente,
                "Sin datos"
        );

        double ingresoFuenteMasRentable = ingresosPorFuente.getOrDefault(
                fuenteMasRentable,
                0.0
        );

        List<String> recomendaciones = generarRecomendaciones(
                ingresoTotal,
                totalFacturas,
                usuarioMayorFacturacion,
                mayorFacturacionUsuario,
                fuenteMasRentable,
                ingresoFuenteMasRentable,
                descuentosTotal
        );

        return new ReporteFacturacionDTO(
                ingresoTotal,
                subtotalTotal,
                impuestosTotal,
                descuentosTotal,
                totalFacturas,
                promedioFactura,
                usuarioMayorFacturacion,
                redondear(mayorFacturacionUsuario),
                fuenteMasRentable,
                redondear(ingresoFuenteMasRentable),
                ingresosPorUsuario,
                ingresosPorFuente,
                ingresosPorTipoFacturacion,
                recomendaciones
        );
    }

    private Map<String, String> obtenerMapaUsuarios() {
        Map<String, String> mapa = new HashMap<>();

        for (Usuario usuario : usuarioRepository.findAll()) {
            mapa.put(usuario.getId(), usuario.getNombre());
        }

        return mapa;
    }

    private List<String> generarRecomendaciones(
            double ingresoTotal,
            int totalFacturas,
            String usuarioMayorFacturacion,
            double mayorFacturacionUsuario,
            String fuenteMasRentable,
            double ingresoFuenteMasRentable,
            double descuentosTotal
    ) {
        List<String> recomendaciones = new ArrayList<>();

        if (totalFacturas == 0) {
            recomendaciones.add("No hay facturas registradas para el periodo seleccionado.");
            return recomendaciones;
        }

        recomendaciones.add(
                "El ingreso total del periodo es $" +
                        ingresoTotal +
                        ", distribuido en " +
                        totalFacturas +
                        " facturas."
        );

        recomendaciones.add(
                "El usuario con mayor facturación es " +
                        usuarioMayorFacturacion +
                        ", con $" +
                        mayorFacturacionUsuario +
                        " acumulados."
        );

        recomendaciones.add(
                "La fuente energética más rentable es " +
                        fuenteMasRentable +
                        ", con $" +
                        ingresoFuenteMasRentable +
                        " facturados."
        );

        if (descuentosTotal > ingresoTotal * 0.1) {
            recomendaciones.add(
                    "Los descuentos representan una proporción importante del ingreso. Conviene revisar políticas de beneficios por energía renovable."
            );
        }

        if (totalFacturas < 5) {
            recomendaciones.add(
                    "El volumen de facturación aún es bajo. Se recomienda generar más facturas para obtener conclusiones más sólidas."
            );
        }

        return recomendaciones;
    }

    private String obtenerClaveMayorValor(Map<String, Double> datos, String valorDefecto) {
        return datos.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(valorDefecto);
    }

    private String normalizarTexto(String valor, String defecto) {
        if (valor == null || valor.isBlank()) {
            return defecto;
        }

        return valor.trim();
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
            default -> "semana";
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

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}