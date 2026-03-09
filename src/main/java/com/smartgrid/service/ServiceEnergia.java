package com.smartgrid.service;

import org.springframework.stereotype.Service;

import com.smartgrid.patterns.abstractfactory.BalanceadorEnergia;
import com.smartgrid.patterns.abstractfactory.CalculadorEficiencia;
import com.smartgrid.patterns.abstractfactory.EnergiaFactory;
import com.smartgrid.patterns.abstractfactory.MonitorEnergia;
import com.smartgrid.patterns.abstractfactory.SolarFactory;

@Service
public class ServiceEnergia {
	
	
	public String procesarEnergia(String tipoEnergia) {

        EnergiaFactory factory;

        switch (tipoEnergia.toUpperCase()) {

            case "SOLAR":
                factory = new SolarFactory();
                break;

            default:
                throw new RuntimeException("Tipo de energía no soportado");
        }
        
        MonitorEnergia monitor = factory.crearMonitor();
        BalanceadorEnergia balanceador = factory.crearBalanceador();
        CalculadorEficiencia calculador = factory.crearCalculador();

        return monitor.monitorear() + "\n" +
               balanceador.balancear() + "\n" +
               calculador.calcular();
    }


}
