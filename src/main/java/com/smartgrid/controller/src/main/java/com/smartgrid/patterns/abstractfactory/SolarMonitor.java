package com.smartgrid.patterns.abstractfactory;

public class SolarMonitor implements MonitorEnergia {

	@Override
	public String monitorear() {
		return "Monitoreando producción de energía solar";
	}

}
