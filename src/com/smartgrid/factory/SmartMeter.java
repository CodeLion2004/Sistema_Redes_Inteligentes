package com.smartgrid.factory;

public class SmartMeter implements Device {

    private double consumption;

    public SmartMeter(double consumption) {

        this.consumption = consumption;
    }

    @Override
    public void monitor() {

        System.out.println("Smart Meter monitoreando consumo: " + consumption + " kWh");
    }

    @Override
    public double getConsumption() {

        return consumption;
    }

    @Override
    public String getName() {

        return "Smart Meter";
    }

}