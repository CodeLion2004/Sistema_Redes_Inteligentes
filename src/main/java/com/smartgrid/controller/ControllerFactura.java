package com.smartgrid.controller;

import com.smartgrid.service.ServiceFactura;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/factura")
public class ControllerFactura {
	
    private final ServiceFactura facturaService;

	public ControllerFactura(ServiceFactura facturaService) {
		this.facturaService = facturaService;
	}
    
	 @GetMapping("/{tipo}/{consumo}/{usuario}")
	    public double calcularFactura(@PathVariable String tipo,
	                                  @PathVariable double consumo,
	                                  @PathVariable String usuario) {

	        return facturaService.calcularFactura(tipo, consumo, usuario);
	    }
    


}
