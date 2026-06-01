package com.smartgrid.dto;

import java.util.List;
import java.util.Map;

public class ReporteFacturacionDTO {

    private double ingresoTotal;
    private double subtotalTotal;
    private double impuestosTotal;
    private double descuentosTotal;

    private int totalFacturas;
    private double promedioFactura;

    private String usuarioMayorFacturacion;
    private double mayorFacturacionUsuario;

    private String fuenteMasRentable;
    private double ingresoFuenteMasRentable;

    private Map<String, Double> ingresosPorUsuario;
    private Map<String, Double> ingresosPorFuente;
    private Map<String, Double> ingresosPorTipoFacturacion;

    private List<String> recomendaciones;

    public ReporteFacturacionDTO(
            double ingresoTotal,
            double subtotalTotal,
            double impuestosTotal,
            double descuentosTotal,
            int totalFacturas,
            double promedioFactura,
            String usuarioMayorFacturacion,
            double mayorFacturacionUsuario,
            String fuenteMasRentable,
            double ingresoFuenteMasRentable,
            Map<String, Double> ingresosPorUsuario,
            Map<String, Double> ingresosPorFuente,
            Map<String, Double> ingresosPorTipoFacturacion,
            List<String> recomendaciones
    ) {
        this.ingresoTotal = ingresoTotal;
        this.subtotalTotal = subtotalTotal;
        this.impuestosTotal = impuestosTotal;
        this.descuentosTotal = descuentosTotal;
        this.totalFacturas = totalFacturas;
        this.promedioFactura = promedioFactura;
        this.usuarioMayorFacturacion = usuarioMayorFacturacion;
        this.mayorFacturacionUsuario = mayorFacturacionUsuario;
        this.fuenteMasRentable = fuenteMasRentable;
        this.ingresoFuenteMasRentable = ingresoFuenteMasRentable;
        this.ingresosPorUsuario = ingresosPorUsuario;
        this.ingresosPorFuente = ingresosPorFuente;
        this.ingresosPorTipoFacturacion = ingresosPorTipoFacturacion;
        this.recomendaciones = recomendaciones;
    }

    public double getIngresoTotal() {
        return ingresoTotal;
    }

    public double getSubtotalTotal() {
        return subtotalTotal;
    }

    public double getImpuestosTotal() {
        return impuestosTotal;
    }

    public double getDescuentosTotal() {
        return descuentosTotal;
    }

    public int getTotalFacturas() {
        return totalFacturas;
    }

    public double getPromedioFactura() {
        return promedioFactura;
    }

    public String getUsuarioMayorFacturacion() {
        return usuarioMayorFacturacion;
    }

    public double getMayorFacturacionUsuario() {
        return mayorFacturacionUsuario;
    }

    public String getFuenteMasRentable() {
        return fuenteMasRentable;
    }

    public double getIngresoFuenteMasRentable() {
        return ingresoFuenteMasRentable;
    }

    public Map<String, Double> getIngresosPorUsuario() {
        return ingresosPorUsuario;
    }

    public Map<String, Double> getIngresosPorFuente() {
        return ingresosPorFuente;
    }

    public Map<String, Double> getIngresosPorTipoFacturacion() {
        return ingresosPorTipoFacturacion;
    }

    public List<String> getRecomendaciones() {
        return recomendaciones;
    }
}