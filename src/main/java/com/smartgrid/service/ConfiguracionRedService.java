package com.smartgrid.service;

import org.springframework.stereotype.Service;

import com.smartgrid.model.ConfiguracionRed;
import com.smartgrid.patterns.prototype.RegistroConfiguraciones;

@Service
public class ConfiguracionRedService {
	
	public ConfiguracionRed crearConfiguracionPersonalizada(String tipo, String nuevaFuente, double nuevoLimite) {
        ConfiguracionRed config = RegistroConfiguraciones.obtenerClon(tipo);
        config.setFuentePreferida(nuevaFuente);
        config.setLimiteConsumo(nuevoLimite);
        return config;
    }

}
