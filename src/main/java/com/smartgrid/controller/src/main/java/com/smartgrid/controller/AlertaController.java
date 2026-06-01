package com.smartgrid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrid.dto.AlertaResponse;
import com.smartgrid.service.AlertaService;



@RestController
@RequestMapping("/alertas")
public class AlertaController {

	private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }
	
    
    @GetMapping("/admin/consumo/{idDispositivo}/{consumo}")
    public AlertaResponse alertaConsumoAdmin(@PathVariable String idDispositivo,
                                             @PathVariable double consumo) {
        return alertaService.generarAlertaConsumoAltoAdmin(idDispositivo, consumo);
    }

    @GetMapping("/usuario/consumo/{idDispositivo}/{consumo}")
    public AlertaResponse alertaConsumoUsuario(@PathVariable String idDispositivo,
                                               @PathVariable double consumo) {
        return alertaService.generarAlertaConsumoAltoUsuario(idDispositivo, consumo);
    }

    @GetMapping("/admin/tarifa/{tipoUsuario}/{nuevaTarifa}")
    public AlertaResponse alertaTarifaAdmin(@PathVariable String tipoUsuario,
                                            @PathVariable double nuevaTarifa) {
        return alertaService.generarAlertaTarifaAdmin(tipoUsuario, nuevaTarifa);
    }

    @GetMapping("/usuario/tarifa/{tipoUsuario}/{nuevaTarifa}")
    public AlertaResponse alertaTarifaUsuario(@PathVariable String tipoUsuario,
                                              @PathVariable double nuevaTarifa) {
        return alertaService.generarAlertaTarifaUsuario(tipoUsuario, nuevaTarifa);
    }
}
