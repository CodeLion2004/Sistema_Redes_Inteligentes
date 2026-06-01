package com.smartgrid.dto;

public class DetalleAlertaZonaDTO {

    private String zona;
    private int totalUsuarios;
    private int totalConsumos;
    private double consumoTotal;
    private double consumoPromedio;
    private int alertasPreventivas;
    private int alertasCriticas;
    private int totalAlertas;
    private String nivelRiesgo;
    private String recomendacion;

    public DetalleAlertaZonaDTO() {
    }

    public DetalleAlertaZonaDTO(String zona,
                                int totalUsuarios,
                                int totalConsumos,
                                double consumoTotal,
                                double consumoPromedio,
                                int alertasPreventivas,
                                int alertasCriticas,
                                int totalAlertas,
                                String nivelRiesgo,
                                String recomendacion) {
        this.zona = zona;
        this.totalUsuarios = totalUsuarios;
        this.totalConsumos = totalConsumos;
        this.consumoTotal = consumoTotal;
        this.consumoPromedio = consumoPromedio;
        this.alertasPreventivas = alertasPreventivas;
        this.alertasCriticas = alertasCriticas;
        this.totalAlertas = totalAlertas;
        this.nivelRiesgo = nivelRiesgo;
        this.recomendacion = recomendacion;
    }

    public String getZona() {
        return zona;
    }

    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    public int getTotalConsumos() {
        return totalConsumos;
    }

    public double getConsumoTotal() {
        return consumoTotal;
    }

    public double getConsumoPromedio() {
        return consumoPromedio;
    }

    public int getAlertasPreventivas() {
        return alertasPreventivas;
    }

    public int getAlertasCriticas() {
        return alertasCriticas;
    }

    public int getTotalAlertas() {
        return totalAlertas;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getRecomendacion() {
        return recomendacion;
    }
}