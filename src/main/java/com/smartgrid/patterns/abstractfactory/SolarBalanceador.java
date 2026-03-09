package com.smartgrid.patterns.abstractfactory;

public class SolarBalanceador implements BalanceadorEnergia{

	@Override
	public String balancear() {
		return  "Balanceando carga con paneles solares";
	}

}
