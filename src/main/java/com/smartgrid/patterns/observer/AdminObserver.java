package com.smartgrid.patterns.observer;

public class AdminObserver implements ObservadorEnergia {

    @Override
    public void actualizar(String mensaje) {

        System.out.println("ADMIN: " + mensaje);

    }
}