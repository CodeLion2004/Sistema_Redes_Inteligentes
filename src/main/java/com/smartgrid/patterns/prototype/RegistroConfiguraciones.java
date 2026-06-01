package com.smartgrid.patterns.prototype;

import java.util.HashMap;
import java.util.Map;
import com.smartgrid.model.ConfiguracionRed;

public class RegistroConfiguraciones {
	
	private static final Map<String, ConfiguracionRed> prototipos = new HashMap<>();

	
	static {
        prototipos.put("RESIDENCIAL", new ConfiguracionRed(
                "Plantilla Residencial", 500, "MEDIA", "SOLAR", "FacturacionResidencial", "MODERADA"
        ));

        prototipos.put("INDUSTRIAL", new ConfiguracionRed(
                "Plantilla Industrial", 2000, "ALTA", "EOLICA", "FacturacionIndustrial", "ALTA"
        ));
    }
	
	public static ConfiguracionRed obtenerClon(String tipo) {
        ConfiguracionRed prototipo = prototipos.get(tipo.toUpperCase());

        if (prototipo == null) {
            throw new RuntimeException("No existe plantilla para el tipo: " + tipo);
        }

        return prototipo.clone();
    }
	
}
