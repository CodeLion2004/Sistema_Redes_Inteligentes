package com.smartgrid.patterns.abstractfactory;

public class SolarEficiencia implements CalculadorEficiencia{

	@Override
	public String calcular() {
		return "Calculando eficiencia de paneles solares";
	}
	
	
	
}
