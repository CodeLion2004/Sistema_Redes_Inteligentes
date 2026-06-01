package com.smartgrid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrid.model.ConfiguracionRed;
import com.smartgrid.service.ConfiguracionRedService;

@RestController
@RequestMapping("/configuracion-red")
public class ControllerConfiguracionRed {
	
	private final ConfiguracionRedService configuracionRedService;

	public ControllerConfiguracionRed(ConfiguracionRedService configuracionRedService) {
		this.configuracionRedService = configuracionRedService;
	}
	
	@GetMapping("/{tipo}/{fuente}/{limite}")
    public ConfiguracionRed crearConfiguracion(@PathVariable String tipo,
                                               @PathVariable String fuente,
                                               @PathVariable double limite) {
        return configuracionRedService.crearConfiguracionPersonalizada(tipo, fuente, limite);
    }

}
