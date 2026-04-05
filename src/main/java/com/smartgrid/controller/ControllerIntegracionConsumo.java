package com.smartgrid.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrid.dto.MedidorExternoDTO;
import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.service.IntegracionConsumoService;

@RestController
@RequestMapping("/integracion")
public class ControllerIntegracionConsumo {

	
	private final IntegracionConsumoService integracionConsumoService;
	
	public ControllerIntegracionConsumo(IntegracionConsumoService integracionConsumoService) {
        this.integracionConsumoService = integracionConsumoService;
    }
	
	@PostMapping("/medidor-externo")
    public ConsumoEnergetico recibirMedidorExterno(@RequestBody MedidorExternoDTO dto) {
        return integracionConsumoService.guardarDesdeMedidorExterno(dto);
    }
}
