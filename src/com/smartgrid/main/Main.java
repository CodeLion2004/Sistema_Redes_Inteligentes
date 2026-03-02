package com.smartgrid.main;

import com.smartgrid.factory.Device;
import com.smartgrid.factory.DeviceFactory;
import com.smartgrid.singleton.SmartGridManager;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== SISTEMA SMART GRID ===");

        // Obtener instancia Singleton
        SmartGridManager manager = SmartGridManager.INSTANCE;

        // Crear dispositivos usando Factory Method
        Device meter = DeviceFactory.createDevice("METER", 150);
        Device solar = DeviceFactory.createDevice("SOLAR", 80);
        Device wind = DeviceFactory.createDevice("WIND", 50);

        // Registrar dispositivos
        manager.registerDevice(meter);
        manager.registerDevice(solar);
        manager.registerDevice(wind);

        // Monitorear
        manager.monitorDevices();

        // Mostrar consumo total
        System.out.println("\nConsumo total del sistema: "
                + manager.getTotalConsumption() + " kWh");
    }
}