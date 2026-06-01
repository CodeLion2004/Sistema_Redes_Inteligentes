package com.smartgrid.dto;

import java.util.List;

public class ReporteAlertasZonaDTO {

    private int totalZonasAnalizadas;
    private int totalAlertas;
    private int totalAlertasPreventivas;
    private int totalAlertasCriticas;
    private String zonaMayorRiesgo;
    private List<DetalleAlertaZonaDTO> zonas;
    private List<String> recomendaciones;

    public ReporteAlertasZonaDTO() {
    }

    public ReporteAlertasZonaDTO(int totalZonasAnalizadas,
                                 int totalAlertas,
                                 int totalAlertasPreventivas,
                                 int totalAlertasCriticas,
                                 String zonaMayorRiesgo,
                                 List<DetalleAlertaZonaDTO> zonas,
                                 List<String> recomendaciones) {
        this.totalZonasAnalizadas = totalZonasAnalizadas;
        this.totalAlertas = totalAlertas;
        this.totalAlertasPreventivas = totalAlertasPreventivas;
        this.totalAlertasCriticas = totalAlertasCriticas;
        this.zonaMayorRiesgo = zonaMayorRiesgo;
        this.zonas = zonas;
        this.recomendaciones = recomendaciones;
    }

    public int getTotalZonasAnalizadas() {
        return totalZonasAnalizadas;
    }

    public int getTotalAlertas() {
        return totalAlertas;
    }

    public int getTotalAlertasPreventivas() {
        return totalAlertasPreventivas;
    }

    public int getTotalAlertasCriticas() {
        return totalAlertasCriticas;
    }

    public String getZonaMayorRiesgo() {
        return zonaMayorRiesgo;
    }

    public List<DetalleAlertaZonaDTO> getZonas() {
        return zonas;
    }

    public List<String> getRecomendaciones() {
        return recomendaciones;
    }
}