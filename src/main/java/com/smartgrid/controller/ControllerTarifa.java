package com.smartgrid.controller;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import com.smartgrid.service.TarifaService;

@RestController
@RequestMapping("/tarifas")
public class ControllerTarifa {
	
    private final TarifaService tarifaService;

	public ControllerTarifa(TarifaService tarifaService) {
		this.tarifaService = tarifaService;
	}

    
	@GetMapping
    public Map<String, Double> listarTarifas() {
        return tarifaService.listarTarifas();
    }
	
	
	 @GetMapping("/{tipoUsuario}")
	    public double obtenerTarifa(@PathVariable String tipoUsuario) {
	        return tarifaService.obtenerTarifa(tipoUsuario);
	    }
	 
	 
	 @PutMapping("/{tipoUsuario}/{nuevaTarifa}")
	    public String actualizarTarifa(@PathVariable String tipoUsuario,@PathVariable double nuevaTarifa) {
		 tarifaService.actualizarTarifa(tipoUsuario, nuevaTarifa);
	        return "Tarifa actualizada correctamente";
	    }
	

}
