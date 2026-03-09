package com.smartgrid.patterns.factorymethod;

public class FacturacionResidencial implements EstrategiaFacturacion{

    @Override
    public double calcularFactura(double consumo, double tarifaBase) {
        return consumo * tarifaBase;
    }
	
	
}
