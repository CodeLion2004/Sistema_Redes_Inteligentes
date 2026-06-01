package com.smartgrid.patterns.abstractfactory;

public interface EnergiaFactory {

	 MonitorEnergia crearMonitor();

	 BalanceadorEnergia crearBalanceador();

	 CalculadorEficiencia crearCalculador();
	
}
