package com.smartgrid.factory;

public class DeviceFactory {

    public static Device createDevice(String type, double value) {

        if (type.equalsIgnoreCase("METER")) {

            return new SmartMeter(value);
        }

        if (type.equalsIgnoreCase("SOLAR")) {

            return new SolarPanel(value);
        }

        if (type.equalsIgnoreCase("WIND")) {

            return new WindTurbine(value);
        }

        throw new IllegalArgumentException("Tipo de dispositivo desconocido");
    }

}