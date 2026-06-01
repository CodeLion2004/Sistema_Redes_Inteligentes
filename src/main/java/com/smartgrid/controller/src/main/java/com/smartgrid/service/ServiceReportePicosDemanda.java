package com.smartgrid.service;

import com.smartgrid.dto.PicosDemandaDTO;
import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.repository.RepositoryConsumoEnergetico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ServiceReportePicosDemanda {

    @Autowired
    private RepositoryConsumoEnergetico repository;

    public PicosDemandaDTO generarReporte(String periodo) {

        List<ConsumoEnergetico> consumos = repository.findAll();

        Map<Integer, Double> consumoPorHora = new TreeMap<>();

        for (int i = 0; i < 24; i++) {
            consumoPorHora.put(i, 0.0);
        }

        for (ConsumoEnergetico consumo : consumos) {

            LocalDateTime fecha = consumo.getMarcaTiempo();

            if (fecha == null) continue;

            int hora = fecha.getHour();

            consumoPorHora.put(
                    hora,
                    consumoPorHora.get(hora) + consumo.getConsumo()
            );
        }

        List<String> horas = new ArrayList<>();
        List<Double> valores = new ArrayList<>();

        double total = 0;

        for (Map.Entry<Integer, Double> entry : consumoPorHora.entrySet()) {

            horas.add(String.format("%02d:00", entry.getKey()));

            double valor = redondear(entry.getValue());

            valores.add(valor);

            total += valor;
        }

        double promedio = total / 24;

        double picoMaximo = valores.stream()
                .max(Double::compareTo)
                .orElse(0.0);

        String horaPico = horas.get(
                valores.indexOf(picoMaximo)
        );

        double umbralPico = promedio * 1.4;

        int cantidadPicos = 0;

        for (double valor : valores) {
            if (valor > umbralPico) {
                cantidadPicos++;
            }
        }

        List<String> recomendaciones = generarRecomendaciones(
                picoMaximo,
                promedio,
                cantidadPicos,
                horaPico
        );

        return new PicosDemandaDTO(
                horas,
                valores,
                redondear(picoMaximo),
                horaPico,
                redondear(promedio),
                cantidadPicos,
                redondear(umbralPico),
                recomendaciones
        );
    }

    private List<String> generarRecomendaciones(
            double picoMaximo,
            double promedio,
            int cantidadPicos,
            String horaPico
    ) {

        List<String> recomendaciones = new ArrayList<>();

        if (cantidadPicos > 5) {
            recomendaciones.add(
                    "Se detectaron múltiples picos de demanda. Se recomienda aplicar estrategias de balanceo de carga."
            );
        }

        if (picoMaximo > promedio * 2) {
            recomendaciones.add(
                    "El pico máximo supera ampliamente el promedio operativo. Conviene revisar infraestructura eléctrica."
            );
        }

        recomendaciones.add(
                "La hora crítica de mayor demanda es " + horaPico +
                        ". Se recomienda monitoreo prioritario en esta franja."
        );

        recomendaciones.add(
                "Considerar tarifas dinámicas para desplazar consumo fuera de horas pico."
        );

        return recomendaciones;
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}