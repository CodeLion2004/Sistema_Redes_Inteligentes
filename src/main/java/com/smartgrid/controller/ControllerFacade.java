package com.smartgrid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrid.dto.ResumenDashboardResponse;
import com.smartgrid.service.SmartGridFacade;

@RestController
@RequestMapping("/dashboard")
public class ControllerFacade {
	
	private final SmartGridFacade smartGridFacade;

    public ControllerFacade(SmartGridFacade smartGridFacade) {
        this.smartGridFacade = smartGridFacade;
    }

    @GetMapping("/resumen")
    public ResumenDashboardResponse obtenerResumen() {
        return smartGridFacade.obtenerResumenDashboard();
    }
	

}
