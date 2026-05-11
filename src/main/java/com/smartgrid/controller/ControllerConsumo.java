package com.smartgrid.controller;

import com.smartgrid.dto.TendenciaConsumoDTO;
import com.smartgrid.service.ServiceReporteConsumo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumo")
@CrossOrigin(origins = "*")
public class ControllerConsumo {

    @Autowired
    private ServiceReporteConsumo consumoService;

    @GetMapping("/tendencias")
    public TendenciaConsumoDTO getTendencias(@RequestParam String periodo) {
        return consumoService.obtenerTendencias(periodo);
    }
}