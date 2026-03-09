package com.smartgrid.patterns.abstractfactory;

public class SolarFactory implements EnergiaFactory{
	
	@Override
    public MonitorEnergia crearMonitor() {
        return new SolarMonitor();
    }

    @Override
    public BalanceadorEnergia crearBalanceador() {
        return new SolarBalanceador();
    }

    @Override
    public CalculadorEficiencia crearCalculador() {
        return new SolarEficiencia();
    }

}
