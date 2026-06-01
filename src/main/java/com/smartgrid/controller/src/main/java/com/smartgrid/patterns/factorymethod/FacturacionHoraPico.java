package com.smartgrid.patterns.factorymethod;

public class FacturacionHoraPico implements EstrategiaFacturacion{
	
	 @Override
	    public double calcularFactura(double consumo, double tarifaBase) {
	        double factorPico = 1.50;
	        return consumo * tarifaBase * factorPico;
	    }

}
