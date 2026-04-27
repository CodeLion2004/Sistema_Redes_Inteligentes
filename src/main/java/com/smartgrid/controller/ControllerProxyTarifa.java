package com.smartgrid.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrid.service.ServiceProxyTarifa;

@RestController
@RequestMapping("/proxy/tarifas")
public class ControllerProxyTarifa {
	
	private final ServiceProxyTarifa serviceProxyTarifa;

    public ControllerProxyTarifa(ServiceProxyTarifa serviceProxyTarifa) {
        this.serviceProxyTarifa = serviceProxyTarifa;
    }

    @PutMapping("/actualizar")
    public String actualizarTarifa(@RequestParam String correoUsuario,
                                   @RequestParam String tipoUsuario,
                                   @RequestParam double nuevaTarifa) {

        return serviceProxyTarifa.actualizarTarifaConProxy(
                correoUsuario,
                tipoUsuario,
                nuevaTarifa
        );
    }

}
