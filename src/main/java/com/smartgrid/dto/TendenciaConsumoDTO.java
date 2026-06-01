package com.smartgrid.dto;

import java.util.List;
import java.util.Map;

public class TendenciaConsumoDTO {
    private List<String> fechas;
    private List<Double> valores;
    private double consumoTotal;

    private String periodo;
    private String fechaInicio;
    private String fechaFin;
    private double consumoPromedio;
    private double consumoMaximo;
    private double consumoMinimo;
    private String fechaPico;
    private int totalRegistros;
    private Map<String, Double> consumoPorFuente;
    private List<String> recomendaciones;

    public TendenciaConsumoDTO(List<String> fechas, List<Double> valores, double consumoTotal) {
        this.fechas = fechas;
        this.valores = valores;
        this.consumoTotal = consumoTotal;
    }

    public TendenciaConsumoDTO(
            List<String> fechas,
            List<Double> valores,
            double consumoTotal,
            String periodo,
            String fechaInicio,
            String fechaFin,
            double consumoPromedio,
            double consumoMaximo,
            double consumoMinimo,
            String fechaPico,
            int totalRegistros,
            Map<String, Double> consumoPorFuente,
            List<String> recomendaciones
    ) {
        this.fechas = fechas;
        this.valores = valores;
        this.consumoTotal = consumoTotal;
        this.periodo = periodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.consumoPromedio = consumoPromedio;
        this.consumoMaximo = consumoMaximo;
        this.consumoMinimo = consumoMinimo;
        this.fechaPico = fechaPico;
        this.totalRegistros = totalRegistros;
        this.consumoPorFuente = consumoPorFuente;
        this.recomendaciones = recomendaciones;
    }

    public List<String> getFechas() { return fechas; }
    public List<Double> getValores() { return valores; }
    public double getConsumoTotal() { return consumoTotal; }
    public String getPeriodo() { return periodo; }
    public String getFechaInicio() { return fechaInicio; }
    public String getFechaFin() { return fechaFin; }
    public double getConsumoPromedio() { return consumoPromedio; }
    public double getConsumoMaximo() { return consumoMaximo; }
    public double getConsumoMinimo() { return consumoMinimo; }
    public String getFechaPico() { return fechaPico; }
    public int getTotalRegistros() { return totalRegistros; }
    public Map<String, Double> getConsumoPorFuente() { return consumoPorFuente; }
    public List<String> getRecomendaciones() { return recomendaciones; }
}