package com.smartgrid.factory;

public class SolarPanel implements Device {

    private double generation;

    public SolarPanel(double generation) {

        this.generation = generation;
    }

    @Override
    public void monitor() {

        System.out.println("Panel Solar generando: " + generation + " kWh");
    }

    @Override
    public double getConsumption() {

        return -generation; // negativo porque genera energía
    }

    @Override
    public String getName() {

        return "Solar Panel";
    }

}