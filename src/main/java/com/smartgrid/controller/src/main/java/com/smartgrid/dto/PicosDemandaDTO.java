package com.smartgrid.dto;

import java.util.List;

public class PicosDemandaDTO {

    private List<String> horas;
    private List<Double> consumos;

    private double picoMaximo;
    private String horaPico;

    private double promedioConsumo;
    private int cantidadPicos;

    private double umbralPico;

    private List<String> recomendaciones;

    public PicosDemandaDTO(
            List<String> horas,
            List<Double> consumos,
            double picoMaximo,
            String horaPico,
            double promedioConsumo,
            int cantidadPicos,
            double umbralPico,
            List<String> recomendaciones
    ) {
        this.horas = horas;
        this.consumos = consumos;
        this.picoMaximo = picoMaximo;
        this.horaPico = horaPico;
        this.promedioConsumo = promedioConsumo;
        this.cantidadPicos = cantidadPicos;
        this.umbralPico = umbralPico;
        this.recomendaciones = recomendaciones;
    }

    public List<String> getHoras() {
        return horas;
    }

    public List<Double> getConsumos() {
        return consumos;
    }

    public double getPicoMaximo() {
        return picoMaximo;
    }

    public String getHoraPico() {
        return horaPico;
    }

    public double getPromedioConsumo() {
        return promedioConsumo;
    }

    public int getCantidadPicos() {
        return cantidadPicos;
    }

    public double getUmbralPico() {
        return umbralPico;
    }

    public List<String> getRecomendaciones() {
        return recomendaciones;
    }
}