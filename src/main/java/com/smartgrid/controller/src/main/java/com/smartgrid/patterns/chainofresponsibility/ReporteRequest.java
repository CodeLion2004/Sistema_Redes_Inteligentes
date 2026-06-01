package com.smartgrid.patterns.chainofresponsibility;

public class ReporteRequest {

    private String periodo;
    private String rolUsuario;

    public ReporteRequest() {
    }

    public ReporteRequest(String periodo, String rolUsuario) {
        this.periodo = periodo;
        this.rolUsuario = rolUsuario;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }
}