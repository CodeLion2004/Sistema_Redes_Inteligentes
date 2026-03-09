package com.smartgrid.patterns.singleton;
import java.util.HashMap;
import java.util.Map;

public enum ConfiguracionTarifas {
	INSTANCIA;
	
	private final Map<String, Double> tarifasBase = new HashMap<>();

	
	ConfiguracionTarifas() {
		tarifasBase.put("RESIDENCIAL", 0.15);
	    tarifasBase.put("COMERCIAL", 0.25);
	    tarifasBase.put("INDUSTRIAL", 0.35);
	}
	 
	public double obtenerTarifaBase(String tipoUsuario) {
		return tarifasBase.getOrDefault(tipoUsuario.toUpperCase(), 0.20);
	}

	public void actualizarTarifa(String tipoUsuario, double nuevaTarifa) {
	    tarifasBase.put(tipoUsuario.toUpperCase(), nuevaTarifa);
	}

	public Map<String, Double> obtenerTodas() {
	    return Map.copyOf(tarifasBase);
	}

}
