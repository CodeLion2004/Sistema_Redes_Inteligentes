package com.smartgrid.dto;

import java.util.List;

public class TendenciaConsumoDTO {
    private List<String> fechas;
    private List<Double> valores;
    private double consumoTotal;

    public TendenciaConsumoDTO(List<String> fechas, List<Double> valores, double consumoTotal) {
        this.fechas = fechas;
        this.valores = valores;
        this.consumoTotal = consumoTotal;
    }

    // Getters (necesarios para JSON)
    public List<String> getFechas() { return fechas; }
    public List<Double> getValores() { return valores; }
    public double getConsumoTotal() { return consumoTotal; }
}