package com.smartgrid.patterns.composite;

import java.util.ArrayList;
import java.util.List;

public class GrupoEnergetico implements NodoEnergetico{

	private String nombre;
    private List<NodoEnergetico> nodos;

    public GrupoEnergetico(String nombre) {
        this.nombre = nombre;
        this.nodos = new ArrayList<>();
    }

    public void agregarNodo(NodoEnergetico nodo) {
        nodos.add(nodo);
    }

    public List<NodoEnergetico> getNodos() {
        return nodos;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double obtenerConsumoTotal() {
        double total = 0;
        for (NodoEnergetico nodo : nodos) {
            total += nodo.obtenerConsumoTotal();
        }
        return total;
    }
	
}
