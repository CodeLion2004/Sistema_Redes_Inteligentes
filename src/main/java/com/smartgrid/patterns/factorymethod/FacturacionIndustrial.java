package com.smartgrid.patterns.factorymethod;

public class FacturacionIndustrial implements EstrategiaFacturacion{

	  @Override
	    public double calcularFactura(double consumo, double tarifaBase) {
	        double recargo = 1.20;
	        return consumo * tarifaBase * recargo;
	    }
}
