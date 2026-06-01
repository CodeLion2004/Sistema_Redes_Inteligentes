package com.smartgrid.patterns.command;


public class ComandoRedRequest {

    private String tipoComando;
    private String tipoConfig;
    private Double nuevoLimite;
    private String nuevaFuente;
    private String nuevoNivel;

    public ComandoRedRequest() {}

    public String getTipoComando() { return tipoComando; }
    public void setTipoComando(String tipoComando) { this.tipoComando = tipoComando; }

    public String getTipoConfig() { return tipoConfig; }
    public void setTipoConfig(String tipoConfig) { this.tipoConfig = tipoConfig; }

    public Double getNuevoLimite() { return nuevoLimite; }
    public void setNuevoLimite(Double nuevoLimite) { this.nuevoLimite = nuevoLimite; }

    public String getNuevaFuente() { return nuevaFuente; }
    public void setNuevaFuente(String nuevaFuente) { this.nuevaFuente = nuevaFuente; }

    public String getNuevoNivel() { return nuevoNivel; }
    public void setNuevoNivel(String nuevoNivel) { this.nuevoNivel = nuevoNivel; }
}