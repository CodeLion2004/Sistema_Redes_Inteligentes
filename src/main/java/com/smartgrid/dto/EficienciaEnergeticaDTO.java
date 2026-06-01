package com.smartgrid.dto;

import java.util.List;
import java.util.Map;

public class EficienciaEnergeticaDTO {

    private double consumoTotal;
    private double consumoRenovable;
    private double consumoNoRenovable;
    private double porcentajeRenovable;
    private double porcentajeNoRenovable;

    private String fuenteDominante;
    private String dispositivoMayorConsumo;
    private double mayorConsumoDispositivo;

    private String indiceEficiencia;
    private Map<String, Double> consumoPorFuente;
    private Map<String, Double> consumoPorDispositivo;
    private List<String> recomendaciones;

    public EficienciaEnergeticaDTO(
            double consumoTotal,
            double consumoRenovable,
            double consumoNoRenovable,
            double porcentajeRenovable,
            double porcentajeNoRenovable,
            String fuenteDominante,
            String dispositivoMayorConsumo,
            double mayorConsumoDispositivo,
            String indiceEficiencia,
            Map<String, Double> consumoPorFuente,
            Map<String, Double> consumoPorDispositivo,
            List<String> recomendaciones
    ) {
        this.consumoTotal = consumoTotal;
        this.consumoRenovable = consumoRenovable;
        this.consumoNoRenovable = consumoNoRenovable;
        this.porcentajeRenovable = porcentajeRenovable;
        this.porcentajeNoRenovable = porcentajeNoRenovable;
        this.fuenteDominante = fuenteDominante;
        this.dispositivoMayorConsumo = dispositivoMayorConsumo;
        this.mayorConsumoDispositivo = mayorConsumoDispositivo;
        this.indiceEficiencia = indiceEficiencia;
        this.consumoPorFuente = consumoPorFuente;
        this.consumoPorDispositivo = consumoPorDispositivo;
        this.recomendaciones = recomendaciones;
    }

    public double getConsumoTotal() {
        return consumoTotal;
    }

    public double getConsumoRenovable() {
        return consumoRenovable;
    }

    public double getConsumoNoRenovable() {
        return consumoNoRenovable;
    }

    public double getPorcentajeRenovable() {
        return porcentajeRenovable;
    }

    public double getPorcentajeNoRenovable() {
        return porcentajeNoRenovable;
    }

    public String getFuenteDominante() {
        return fuenteDominante;
    }

    public String getDispositivoMayorConsumo() {
        return dispositivoMayorConsumo;
    }

    public double getMayorConsumoDispositivo() {
        return mayorConsumoDispositivo;
    }

    public String getIndiceEficiencia() {
        return indiceEficiencia;
    }

    public Map<String, Double> getConsumoPorFuente() {
        return consumoPorFuente;
    }

    public Map<String, Double> getConsumoPorDispositivo() {
        return consumoPorDispositivo;
    }

    public List<String> getRecomendaciones() {
        return recomendaciones;
    }
}