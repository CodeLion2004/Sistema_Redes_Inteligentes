package com.smartgrid.service;

import org.springframework.stereotype.Service;

import com.smartgrid.patterns.factorymethod.EstrategiaFacturacion;
import com.smartgrid.patterns.factorymethod.FacturacionFactory;
import com.smartgrid.patterns.singleton.ConfiguracionTarifas;

@Service
public class ServiceFactura {
	
	public double calcularFactura(String tipoFacturacion, double consumo, String tipoUsuario) {

        double tarifaBase = ConfiguracionTarifas.INSTANCIA.obtenerTarifaBase(tipoUsuario);

        EstrategiaFacturacion estrategia = FacturacionFactory.crearEstrategia(tipoFacturacion);

        return estrategia.calcularFactura(consumo, tarifaBase);
    }

}
