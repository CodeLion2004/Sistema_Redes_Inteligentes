package com.smartgrid.patterns.chainofresponsibility;

public abstract class ReporteValidator {

    private ReporteValidator siguiente;

    public ReporteValidator enlazarCon(ReporteValidator siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public final void validar(ReporteRequest request) {
        validarActual(request);

        if (siguiente != null) {
            siguiente.validar(request);
        }
    }

    protected abstract void validarActual(ReporteRequest request);
}