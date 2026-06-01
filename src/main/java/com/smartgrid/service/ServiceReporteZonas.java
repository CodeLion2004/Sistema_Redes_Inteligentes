package com.smartgrid.service;

import com.smartgrid.dto.NodoEnergeticoResponse;
import com.smartgrid.dto.ZonaConsumoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceReporteZonas {

    private final ServiceCompositeEnergia serviceCompositeEnergia;

    public ServiceReporteZonas(ServiceCompositeEnergia serviceCompositeEnergia) {
        this.serviceCompositeEnergia = serviceCompositeEnergia;
    }

    public ZonaConsumoDTO generarReporte() {

        NodoEnergeticoResponse redPrincipal =
                serviceCompositeEnergia.construirRedEnergetica();

        Map<String, Double> consumoPorZona = new LinkedHashMap<>();
        Map<String, Integer> registrosPorZona = new LinkedHashMap<>();

        for (NodoEnergeticoResponse zona : redPrincipal.getHijos()) {
            consumoPorZona.put(
                    zona.getNombre(),
                    redondear(zona.getConsumoTotal())
            );

            registrosPorZona.put(
                    zona.getNombre(),
                    zona.getHijos() != null ? zona.getHijos().size() : 0
            );
        }

        double consumoTotal = redondear(redPrincipal.getConsumoTotal());

        String zonaMayorConsumo = obtenerZonaMayorConsumo(consumoPorZona);
        double mayorConsumoZona = consumoPorZona.getOrDefault(zonaMayorConsumo, 0.0);

        List<String> zonasCriticas = obtenerZonasCriticas(consumoPorZona, consumoTotal);

        List<String> recomendaciones = generarRecomendaciones(
                consumoTotal,
                zonaMayorConsumo,
                mayorConsumoZona,
                zonasCriticas
        );

        return new ZonaConsumoDTO(
                consumoTotal,
                zonaMayorConsumo,
                redondear(mayorConsumoZona),
                consumoPorZona.size(),
                consumoPorZona,
                registrosPorZona,
                zonasCriticas,
                recomendaciones
        );
    }

    private String obtenerZonaMayorConsumo(Map<String, Double> datos) {
        return datos.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Sin datos");
    }

    private List<String> obtenerZonasCriticas(
            Map<String, Double> consumoPorZona,
            double consumoTotal
    ) {
        List<String> zonasCriticas = new ArrayList<>();

        if (consumoTotal <= 0) {
            return zonasCriticas;
        }

        for (Map.Entry<String, Double> entry : consumoPorZona.entrySet()) {
            double porcentaje = (entry.getValue() / consumoTotal) * 100;

            if (porcentaje >= 35) {
                zonasCriticas.add(entry.getKey());
            }
        }

        return zonasCriticas;
    }

    private List<String> generarRecomendaciones(
            double consumoTotal,
            String zonaMayorConsumo,
            double mayorConsumoZona,
            List<String> zonasCriticas
    ) {
        List<String> recomendaciones = new ArrayList<>();

        if (consumoTotal <= 0) {
            recomendaciones.add("No hay consumos registrados para construir el análisis por zonas.");
            return recomendaciones;
        }

        recomendaciones.add(
                "La zona con mayor consumo es " + zonaMayorConsumo +
                        ", con " + mayorConsumoZona + " kWh acumulados."
        );

        if (!zonasCriticas.isEmpty()) {
            recomendaciones.add(
                    "Se detectaron zonas críticas con alta concentración de consumo. Se recomienda priorizar monitoreo operativo."
            );
        }

        if (zonasCriticas.size() > 1) {
            recomendaciones.add(
                    "Hay múltiples zonas con alta demanda. Conviene revisar balanceo de carga entre zonas."
            );
        }

        if (zonasCriticas.isEmpty()) {
            recomendaciones.add(
                    "La distribución energética se encuentra balanceada entre las zonas analizadas."
            );
        }

        recomendaciones.add(
                "Usar la lectura por zonas para planificar mantenimiento preventivo, redistribución de carga y expansión de infraestructura."
        );

        return recomendaciones;
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}