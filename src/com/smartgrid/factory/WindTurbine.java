package com.smartgrid.factory;

public class WindTurbine implements Device {

    private double generation;

    public WindTurbine(double generation) {

        this.generation = generation;
    }

    @Override
    public void monitor() {

        System.out.println("Turbina Eólica generando: " + generation + " kWh");
    }

    @Override
    public double getConsumption() {

        return -generation;
    }

    @Override
    public String getName() {

        return "Wind Turbine";
    }

}