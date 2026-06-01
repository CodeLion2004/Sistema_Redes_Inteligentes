package com.smartgrid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrid.service.ServiceEnergia;




@RestController
@RequestMapping("/energia")
public class ControllerEnergia {
	
	private final ServiceEnergia serviceEnergia;

	public ControllerEnergia(ServiceEnergia serviceEnergia) {
		this.serviceEnergia = serviceEnergia;
	}
	
	  @GetMapping("/{tipo}")
	    public String procesar(@PathVariable String tipo) {

	        return serviceEnergia.procesarEnergia(tipo);
	    }

}
