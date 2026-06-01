package com.smartgrid.service;

import com.smartgrid.dto.TendenciaConsumoDTO;
import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.repository.RepositoryConsumoEnergetico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ServiceReporteConsumo {

    @Autowired
    private RepositoryConsumoEnergetico consumoRepository;

    public TendenciaConsumoDTO obtenerTendencias(String periodo) {
        String periodoNormalizado = normalizarPeriodo(periodo);
        LocalDateTime fechaFin = LocalDateTime.now();
        LocalDateTime fechaInicio = calcularFechaInicio(periodoNormalizado, fechaFin);

        List<ConsumoEnergetico> consumos = obtenerConsumos(periodoNormalizado, fechaInicio, fechaFin);
        consumos.sort(Comparator.comparing(ConsumoEnergetico::getMarcaTiempo, Comparator.nullsLast(Comparator.naturalOrder())));

        Map<String, Double> consumoPorFecha = new LinkedHashMap<>();
        Map<String, Double> consumoPorFuente = new LinkedHashMap<>();

        for (ConsumoEnergetico consumo : consumos) {
            if (consumo.getMarcaTiempo() == null) {
                continue;
            }

            String fecha = consumo.getMarcaTiempo().toLocalDate().toString();
            consumoPorFecha.merge(fecha, consumo.getConsumo(), Double::sum);

            String fuente = consumo.getFuenteEnergia() == null || consumo.getFuenteEnergia().isBlank()
                    ? "No especificada"
                    : consumo.getFuenteEnergia();

            consumoPorFuente.merge(fuente, consumo.getConsumo(), Double::sum);
        }

        List<String> fechas = new ArrayList<>(consumoPorFecha.keySet());
        List<Double> valores = new ArrayList<>(consumoPorFecha.values());

        double consumoTotal = redondear(valores.stream().mapToDouble(Double::doubleValue).sum());
        double consumoPromedio = valores.isEmpty() ? 0 : redondear(consumoTotal / valores.size());
        double consumoMaximo = valores.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double consumoMinimo = valores.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        String fechaPico = obtenerFechaPico(consumoPorFecha, consumoMaximo);

        consumoPorFecha.replaceAll((fecha, valor) -> redondear(valor));
        consumoPorFuente.replaceAll((fuente, valor) -> redondear(valor));

        List<String> recomendaciones = generarRecomendaciones(
                consumoTotal,
                consumoPromedio,
                consumoMaximo,
                consumoMinimo,
                fechaPico,
                consumoPorFuente,
                valores.size()
        );

        return new TendenciaConsumoDTO(
                fechas,
                valores.stream().map(this::redondear).toList(),
                consumoTotal,
                periodoNormalizado,
                periodoNormalizado.equals("todo") ? "Histórico completo" : fechaInicio.toLocalDate().toString(),
                fechaFin.toLocalDate().toString(),
                consumoPromedio,
                redondear(consumoMaximo),
                redondear(consumoMinimo),
                fechaPico,
                consumos.size(),
                consumoPorFuente,
                recomendaciones
        );
    }

    private List<ConsumoEnergetico> obtenerConsumos(String periodo, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (periodo.equals("todo")) {
            return consumoRepository.findAll();
        }

        return consumoRepository.findByMarcaTiempoBetween(fechaInicio, fechaFin);
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

    private String obtenerFechaPico(Map<String, Double> consumoPorFecha, double consumoMaximo) {
        return consumoPorFecha.entrySet().stream()
                .filter(entry -> Double.compare(entry.getValue(), consumoMaximo) == 0)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("Sin datos");
    }

    private List<String> generarRecomendaciones(
            double consumoTotal,
            double consumoPromedio,
            double consumoMaximo,
            double consumoMinimo,
            String fechaPico,
            Map<String, Double> consumoPorFuente,
            int diasConDatos
    ) {
        List<String> recomendaciones = new ArrayList<>();

        if (diasConDatos == 0) {
            recomendaciones.add("No hay consumos registrados para el periodo seleccionado. Verifica la integración de medidores o amplía el rango de análisis.");
            return recomendaciones;
        }

        if (consumoMaximo > consumoPromedio * 1.5) {
            recomendaciones.add("El día " + fechaPico + " presentó un pico superior al 50% del promedio. Conviene revisar carga, usuarios o dispositivos asociados a esa fecha.");
        }

        if (consumoMinimo > 0 && consumoMaximo > consumoMinimo * 2) {
            recomendaciones.add("Existe alta variabilidad entre el consumo mínimo y máximo. Se recomienda evaluar estrategias de balanceo de carga.");
        }

        consumoPorFuente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(fuentePrincipal -> recomendaciones.add("La fuente con mayor participación es " + fuentePrincipal.getKey() + ". Analiza si conviene diversificar o priorizar fuentes renovables."));

        if (consumoTotal > 10000) {
            recomendaciones.add("El consumo total del periodo es alto. Considera activar alertas preventivas y revisar tarifas dinámicas para horas de mayor demanda.");
        }

        if (recomendaciones.isEmpty()) {
            recomendaciones.add("El comportamiento del consumo es estable para el periodo seleccionado. Mantén el monitoreo continuo.");
        }

        return recomendaciones;
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}