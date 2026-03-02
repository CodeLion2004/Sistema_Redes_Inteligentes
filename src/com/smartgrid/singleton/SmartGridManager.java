package com.smartgrid.singleton;

import java.util.ArrayList;
import java.util.List;

import com.smartgrid.factory.Device;

public enum SmartGridManager {

    INSTANCE;

    private List<Device> devices = new ArrayList<>();
    private double totalConsumption = 0;

    // Agregar dispositivo al sistema
    public void registerDevice(Device device) {

        devices.add(device);
        System.out.println("Dispositivo registrado: " + device.getName());
    }

    // Monitorear todos los dispositivos
    public void monitorDevices() {

        System.out.println("\nMonitoreo de dispositivos:");

        for (Device device : devices) {

            device.monitor();
            totalConsumption += device.getConsumption();
        }
    }

    // Obtener consumo total
    public double getTotalConsumption() {

        return totalConsumption;
    }

}