package com.smartgrid.controller;

import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.service.ServiceConsumoEnergetico;

import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/energy")
public class ControllerConsumoEnergetico {
	
	private ServiceConsumoEnergetico service;

	public ControllerConsumoEnergetico(ServiceConsumoEnergetico service) {
		this.service = service;
	}
	
	@PostMapping
	public ConsumoEnergetico save(@RequestBody ConsumoEnergetico consumoEnergetico) {
		return service.save(consumoEnergetico);
	}
	
	
	@GetMapping
	public List<ConsumoEnergetico> findAll(){
		return service.findAll();
	}
}
