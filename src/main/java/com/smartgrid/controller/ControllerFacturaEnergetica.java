package com.smartgrid.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrid.dto.FacturaRequest;
import com.smartgrid.model.FacturaEnergetica;
import com.smartgrid.service.ServiceFacturaEnergetica;

@RestController
@RequestMapping("/facturas")
public class ControllerFacturaEnergetica {
	
	private final ServiceFacturaEnergetica facturaEnergeticaService;

	public ControllerFacturaEnergetica(ServiceFacturaEnergetica facturaEnergeticaService) {
		this.facturaEnergeticaService = facturaEnergeticaService;
	}
	
	
	 @PostMapping
	    public FacturaEnergetica generarFactura(@RequestBody FacturaRequest request) {
	        return facturaEnergeticaService.generarFactura(request);
	    }

	    @GetMapping
	    public List<FacturaEnergetica> listarFacturas() {
	        return facturaEnergeticaService.listarFacturas();
	    }
	

}
