package com.smartgrid.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		 public List<FacturaEnergetica> listarFacturas(@RequestParam(required = false) String usuarioId) {
		     if (usuarioId != null && !usuarioId.isEmpty()) {
		         return facturaEnergeticaService.listarFacturasPorUsuario(usuarioId);
		     }
		     return facturaEnergeticaService.listarFacturas();
		 }
	    
	    @DeleteMapping("/{id}")
	    public void eliminarFactura(@PathVariable String id) {
	        facturaEnergeticaService.eliminarFactura(id);
	    }
	    
	    @PutMapping("/{id}/estado/{accion}")
	    public FacturaEnergetica cambiarEstadoFactura(
	            @PathVariable String id,
	            @PathVariable String accion
	    ) {
	        return facturaEnergeticaService.cambiarEstadoFactura(id, accion);
	    }
	

}
