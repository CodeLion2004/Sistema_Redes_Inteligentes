package com.smartgrid.dto;

import java.util.List;
import java.util.Map;

public class ZonaConsumoDTO {

    private double consumoTotal;

    private String zonaMayorConsumo;

    private double mayorConsumoZona;

    private int totalZonasAnalizadas;

    private Map<String, Double> consumoPorZona;

    private Map<String, Integer> registrosPorZona;

    private List<String> zonasCriticas;

    private List<String> recomendaciones;

    public ZonaConsumoDTO(
            double consumoTotal,
            String zonaMayorConsumo,
            double mayorConsumoZona,
            int totalZonasAnalizadas,
            Map<String, Double> consumoPorZona,
            Map<String, Integer> registrosPorZona,
            List<String> zonasCriticas,
            List<String> recomendaciones
    ) {
        this.consumoTotal = consumoTotal;
        this.zonaMayorConsumo = zonaMayorConsumo;
        this.mayorConsumoZona = mayorConsumoZona;
        this.totalZonasAnalizadas = totalZonasAnalizadas;
        this.consumoPorZona = consumoPorZona;
        this.registrosPorZona = registrosPorZona;
        this.zonasCriticas = zonasCriticas;
        this.recomendaciones = recomendaciones;
    }

    public double getConsumoTotal() {
        return consumoTotal;
    }

    public void setConsumoTotal(double consumoTotal) {
        this.consumoTotal = consumoTotal;
    }

    public String getZonaMayorConsumo() {
        return zonaMayorConsumo;
    }

    public void setZonaMayorConsumo(String zonaMayorConsumo) {
        this.zonaMayorConsumo = zonaMayorConsumo;
    }

    public double getMayorConsumoZona() {
        return mayorConsumoZona;
    }

    public void setMayorConsumoZona(double mayorConsumoZona) {
        this.mayorConsumoZona = mayorConsumoZona;
    }

    public int getTotalZonasAnalizadas() {
        return totalZonasAnalizadas;
    }

    public void setTotalZonasAnalizadas(int totalZonasAnalizadas) {
        this.totalZonasAnalizadas = totalZonasAnalizadas;
    }

    public Map<String, Double> getConsumoPorZona() {
        return consumoPorZona;
    }

    public void setConsumoPorZona(Map<String, Double> consumoPorZona) {
        this.consumoPorZona = consumoPorZona;
    }

    public Map<String, Integer> getRegistrosPorZona() {
        return registrosPorZona;
    }

    public void setRegistrosPorZona(Map<String, Integer> registrosPorZona) {
        this.registrosPorZona = registrosPorZona;
    }

    public List<String> getZonasCriticas() {
        return zonasCriticas;
    }

    public void setZonasCriticas(List<String> zonasCriticas) {
        this.zonasCriticas = zonasCriticas;
    }

    public List<String> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<String> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
}