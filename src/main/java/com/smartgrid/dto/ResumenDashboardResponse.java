package com.smartgrid.dto;

import java.util.Map;

public class ResumenDashboardResponse {
	
	private long totalUsuarios;
    private long totalConsumos;
    private long totalFacturas;
    private Map<String, Double> tarifas;
    private double consumoTotalRed;

    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(long totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public long getTotalConsumos() {
        return totalConsumos;
    }

    public void setTotalConsumos(long totalConsumos) {
        this.totalConsumos = totalConsumos;
    }

    public long getTotalFacturas() {
        return totalFacturas;
    }

    public void setTotalFacturas(long totalFacturas) {
        this.totalFacturas = totalFacturas;
    }

    public Map<String, Double> getTarifas() {
        return tarifas;
    }

    public void setTarifas(Map<String, Double> tarifas) {
        this.tarifas = tarifas;
    }

    public double getConsumoTotalRed() {
        return consumoTotalRed;
    }

    public void setConsumoTotalRed(double consumoTotalRed) {
        this.consumoTotalRed = consumoTotalRed;
    }

}
