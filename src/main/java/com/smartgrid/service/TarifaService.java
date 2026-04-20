package com.smartgrid.service;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.smartgrid.patterns.singleton.ConfiguracionTarifas;

@Service
public class TarifaService {
	
	public double obtenerTarifa(String tipoUsuario) {
        return ConfiguracionTarifas.INSTANCIA.obtenerTarifaBase(tipoUsuario);
    }

    public Map<String, Double> listarTarifas() {
        return ConfiguracionTarifas.INSTANCIA.obtenerTodas();
    }

    public void actualizarTarifa(String tipoUsuario, double nuevaTarifa) {
        ConfiguracionTarifas.INSTANCIA.actualizarTarifa(tipoUsuario, nuevaTarifa);
    }
    
}
