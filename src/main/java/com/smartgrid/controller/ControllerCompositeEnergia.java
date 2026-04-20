package com.smartgrid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrid.dto.NodoEnergeticoResponse;
import com.smartgrid.service.ServiceCompositeEnergia;

@RestController
@RequestMapping("/red-energetica")
public class ControllerCompositeEnergia {


    private final ServiceCompositeEnergia compositeEnergiaService;

    public ControllerCompositeEnergia(ServiceCompositeEnergia compositeEnergiaService) {
        this.compositeEnergiaService = compositeEnergiaService;
    }

    @GetMapping
    public NodoEnergeticoResponse obtenerRedEnergetica() {
        return compositeEnergiaService.construirRedEnergetica();
    }
	
}
