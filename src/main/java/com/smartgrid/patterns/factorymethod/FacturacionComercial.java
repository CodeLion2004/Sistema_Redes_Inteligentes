package com.smartgrid.patterns.factorymethod;

public class FacturacionComercial implements EstrategiaFacturacion{

	 @Override
	    public double calcularFactura(double consumo, double tarifaBase) {
	        return consumo * tarifaBase; // igual que residencial, o con un pequeño recargo
	    }
	
}
