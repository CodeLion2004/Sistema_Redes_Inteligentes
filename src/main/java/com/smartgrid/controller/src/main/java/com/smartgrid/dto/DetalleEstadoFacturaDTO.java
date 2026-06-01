package com.smartgrid.dto;

public class DetalleEstadoFacturaDTO {

    private String estado;
    private int cantidadFacturas;
    private double totalFacturado;
    private double porcentajeFacturas;
    private double porcentajeIngresos;

    public DetalleEstadoFacturaDTO() {
    }

    public DetalleEstadoFacturaDTO(String estado,
                                   int cantidadFacturas,
                                   double totalFacturado,
                                   double porcentajeFacturas,
                                   double porcentajeIngresos) {
        this.estado = estado;
        this.cantidadFacturas = cantidadFacturas;
        this.totalFacturado = totalFacturado;
        this.porcentajeFacturas = porcentajeFacturas;
        this.porcentajeIngresos = porcentajeIngresos;
    }

    public String getEstado() {
        return estado;
    }

    public int getCantidadFacturas() {
        return cantidadFacturas;
    }

    public double getTotalFacturado() {
        return totalFacturado;
    }

    public double getPorcentajeFacturas() {
        return porcentajeFacturas;
    }

    public double getPorcentajeIngresos() {
        return porcentajeIngresos;
    }
}