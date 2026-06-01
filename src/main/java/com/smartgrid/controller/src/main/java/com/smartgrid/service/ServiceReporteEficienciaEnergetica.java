package com.smartgrid.service;

import com.smartgrid.dto.EficienciaEnergeticaDTO;
import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.repository.RepositoryConsumoEnergetico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ServiceReporteEficienciaEnergetica {

    @Autowired
    private RepositoryConsumoEnergetico consumoRepository;

    public EficienciaEnergeticaDTO generarReporte(String periodo) {
        String periodoNormalizado = normalizarPeriodo(periodo);

        LocalDateTime fechaFin = LocalDateTime.now();
        LocalDateTime fechaInicio = calcularFechaInicio(periodoNormalizado, fechaFin);

        List<ConsumoEnergetico> consumos = obtenerConsumos(periodoNormalizado, fechaInicio, fechaFin);

        Map<String, Double> consumoPorFuente = new LinkedHashMap<>();
        Map<String, Double> consumoPorDispositivo = new LinkedHashMap<>();

        double consumoTotal = 0;
        double consumoRenovable = 0;
        double consumoNoRenovable = 0;

        for (ConsumoEnergetico consumo : consumos) {
            double valor = consumo.getConsumo();
            consumoTotal += valor;

            String fuente = normalizarTexto(consumo.getFuenteEnergia(), "No especificada");
            String dispositivo = normalizarTexto(consumo.getIdDispositivo(), "Sin dispositivo");

            consumoPorFuente.merge(fuente, valor, Double::sum);
            consumoPorDispositivo.merge(dispositivo, valor, Double::sum);

            if (esFuenteRenovable(fuente)) {
                consumoRenovable += valor;
            } else {
                consumoNoRenovable += valor;
            }
        }

        consumoTotal = redondear(consumoTotal);
        consumoRenovable = redondear(consumoRenovable);
        consumoNoRenovable = redondear(consumoNoRenovable);

        double porcentajeRenovable = consumoTotal == 0 ? 0 : redondear((consumoRenovable / consumoTotal) * 100);
        double porcentajeNoRenovable = consumoTotal == 0 ? 0 : redondear((consumoNoRenovable / consumoTotal) * 100);

        consumoPorFuente.replaceAll((fuente, valor) -> redondear(valor));
        consumoPorDispositivo.replaceAll((dispositivo, valor) -> redondear(valor));

        String fuenteDominante = obtenerClaveMayorValor(consumoPorFuente, "Sin datos");
        String dispositivoMayorConsumo = obtenerClaveMayorValor(consumoPorDispositivo, "Sin datos");

        double mayorConsumoDispositivo = consumoPorDispositivo.getOrDefault(dispositivoMayorConsumo, 0.0);

        String indiceEficiencia = calcularIndiceEficiencia(porcentajeRenovable);
        List<String> recomendaciones = generarRecomendaciones(
                consumoTotal,
                porcentajeRenovable,
                fuenteDominante,
                dispositivoMayorConsumo,
                mayorConsumoDispositivo
        );

        return new EficienciaEnergeticaDTO(
                consumoTotal,
                consumoRenovable,
                consumoNoRenovable,
                porcentajeRenovable,
                porcentajeNoRenovable,
                fuenteDominante,
                dispositivoMayorConsumo,
                redondear(mayorConsumoDispositivo),
                indiceEficiencia,
                consumoPorFuente,
                consumoPorDispositivo,
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

    private boolean esFuenteRenovable(String fuente) {
        String valor = fuente.toLowerCase(Locale.ROOT);

        return valor.contains("solar")
                || valor.contains("eólica")
                || valor.contains("eolica")
                || valor.contains("hidro")
                || valor.contains("renovable");
    }

    private String normalizarTexto(String valor, String reemplazo) {
        if (valor == null || valor.isBlank()) {
            return reemplazo;
        }

        return valor.trim();
    }

    private String obtenerClaveMayorValor(Map<String, Double> datos, String valorDefecto) {
        return datos.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(valorDefecto);
    }

    private String calcularIndiceEficiencia(double porcentajeRenovable) {
        if (porcentajeRenovable >= 70) {
            return "Alta";
        }

        if (porcentajeRenovable >= 40) {
            return "Media";
        }

        return "Baja";
    }

    private List<String> generarRecomendaciones(
            double consumoTotal,
            double porcentajeRenovable,
            String fuenteDominante,
            String dispositivoMayorConsumo,
            double mayorConsumoDispositivo
    ) {
        List<String> recomendaciones = new ArrayList<>();

        if (consumoTotal == 0) {
            recomendaciones.add("No hay consumos registrados para el periodo seleccionado. Registra lecturas energéticas para generar análisis.");
            return recomendaciones;
        }

        if (porcentajeRenovable < 30) {
            recomendaciones.add("La participación renovable es baja. Se recomienda aumentar el uso de fuentes solares, eólicas o hidroeléctricas.");
        }

        if (porcentajeRenovable >= 70) {
            recomendaciones.add("La matriz energética tiene una alta participación renovable. Mantener esta estrategia mejora la sostenibilidad operativa.");
        }

        recomendaciones.add("La fuente dominante es " + fuenteDominante + ". Evalúa si existe dependencia excesiva de esta fuente energética.");

        if (mayorConsumoDispositivo > consumoTotal * 0.35) {
            recomendaciones.add("El dispositivo " + dispositivoMayorConsumo + " concentra una parte importante del consumo. Conviene revisar su eficiencia o patrón de uso.");
        }

        if (recomendaciones.isEmpty()) {
            recomendaciones.add("El comportamiento energético es equilibrado para el periodo seleccionado.");
        }

        return recomendaciones;
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}