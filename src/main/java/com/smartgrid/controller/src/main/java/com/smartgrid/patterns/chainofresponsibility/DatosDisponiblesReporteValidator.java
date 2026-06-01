package com.smartgrid.patterns.chainofresponsibility;

public class DatosDisponiblesReporteValidator extends ReporteValidator {

    private final int cantidadRegistros;

    public DatosDisponiblesReporteValidator(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    @Override
    protected void validarActual(ReporteRequest request) {
        if (cantidadRegistros <= 0) {
            throw new IllegalStateException("No existen datos disponibles para generar el reporte.");
        }
    }
}