package com.smartgrid.dto;

import java.util.ArrayList;
import java.util.List;

public class NodoEnergeticoResponse {
	
	private String nombre;
    private String tipo;
    private double consumoTotal;
    private List<NodoEnergeticoResponse> hijos = new ArrayList<>();

    public NodoEnergeticoResponse() {
    }

    public NodoEnergeticoResponse(String nombre, String tipo, double consumoTotal) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.consumoTotal = consumoTotal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getConsumoTotal() {
        return consumoTotal;
    }

    public void setConsumoTotal(double consumoTotal) {
        this.consumoTotal = consumoTotal;
    }

    public List<NodoEnergeticoResponse> getHijos() {
        return hijos;
    }

    public void setHijos(List<NodoEnergeticoResponse> hijos) {
        this.hijos = hijos;
    }

    public void agregarHijo(NodoEnergeticoResponse hijo) {
        this.hijos.add(hijo);
    }

}
