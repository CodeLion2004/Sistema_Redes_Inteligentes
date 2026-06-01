package com.smartgrid.dto;

import java.util.List;
import java.util.Map;

public class ReporteFacturasEstadoDTO {

    private int totalFacturas;
    private double totalFacturado;
    private String estadoDominante;
    private List<DetalleEstadoFacturaDTO> estados;
    private Map<String, Double> totalPorEstado;
    private List<String> recomendaciones;

    public ReporteFacturasEstadoDTO() {
    }

    public ReporteFacturasEstadoDTO(int totalFacturas,
                                    double totalFacturado,
                                    String estadoDominante,
                                    List<DetalleEstadoFacturaDTO> estados,
                                    Map<String, Double> totalPorEstado,
                                    List<String> recomendaciones) {
        this.totalFacturas = totalFacturas;
        this.totalFacturado = totalFacturado;
        this.estadoDominante = estadoDominante;
        this.estados = estados;
        this.totalPorEstado = totalPorEstado;
        this.recomendaciones = recomendaciones;
    }

    public int getTotalFacturas() {
        return totalFacturas;
    }

    public double getTotalFacturado() {
        return totalFacturado;
    }

    public String getEstadoDominante() {
        return estadoDominante;
    }

    public List<DetalleEstadoFacturaDTO> getEstados() {
        return estados;
    }

    public Map<String, Double> getTotalPorEstado() {
        return totalPorEstado;
    }

    public List<String> getRecomendaciones() {
        return recomendaciones;
    }
}