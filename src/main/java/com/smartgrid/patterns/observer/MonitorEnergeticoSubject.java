package com.smartgrid.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class MonitorEnergeticoSubject {

    private List<ObservadorEnergia> observadores = new ArrayList<>();

    public void agregarObservador(ObservadorEnergia o){
        observadores.add(o);
    }

    public void eliminarObservador(ObservadorEnergia o){
        observadores.remove(o);
    }

    public void notificar(String mensaje){

        for(ObservadorEnergia o : observadores){
            o.actualizar(mensaje);
        }

    }

    public void detectarConsumo(double consumo){

        if(consumo > 400){

            notificar("Consumo alto detectado: " + consumo + " KW");

        }

    }

}