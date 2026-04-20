package com.smartgrid.patterns.composite;

public class DispositivoEnergetico implements NodoEnergetico{

	private String nombre;
    private double consumo;

    public DispositivoEnergetico(String nombre, double consumo) {
        this.nombre = nombre;
        this.consumo = consumo;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double obtenerConsumoTotal() {
        return consumo;
    }
	
}
