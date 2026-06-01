package com.smartgrid.controller;

import com.smartgrid.dto.EficienciaEnergeticaDTO;
import com.smartgrid.dto.PicosDemandaDTO;
import com.smartgrid.dto.TendenciaConsumoDTO;
import com.smartgrid.dto.ZonaConsumoDTO;
import com.smartgrid.service.ServiceReporteConsumo;
import com.smartgrid.service.ServiceReporteEficienciaEnergetica;
import com.smartgrid.service.ServiceReportePicosDemanda;
import com.smartgrid.service.ServiceReporteZonas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumo")
@CrossOrigin(origins = "*")
public class ControllerConsumo {

    @Autowired
    private ServiceReporteConsumo consumoService;
    
    @Autowired
    private ServiceReportePicosDemanda serviceReportePicosDemanda;
    
    @Autowired
    private ServiceReporteEficienciaEnergetica serviceReporteEficienciaEnergetica;
    
    @Autowired
    private ServiceReporteZonas serviceReporteZonas;

    @GetMapping("/tendencias")
    public TendenciaConsumoDTO getTendencias(@RequestParam String periodo) {
        return consumoService.obtenerTendencias(periodo);
    }
    
    @GetMapping("/picos-demanda")
    public ResponseEntity<PicosDemandaDTO> obtenerPicosDemanda(
            @RequestParam(defaultValue = "semana") String periodo
    ) {

        return ResponseEntity.ok(
                serviceReportePicosDemanda.generarReporte(periodo)
        );
    }
    
    @GetMapping("/eficiencia-energetica")
    public ResponseEntity<EficienciaEnergeticaDTO> obtenerEficienciaEnergetica(
            @RequestParam(defaultValue = "semana") String periodo
    ) {
        return ResponseEntity.ok(
                serviceReporteEficienciaEnergetica.generarReporte(periodo)
        );
    }
    
    @GetMapping("/zonas")
    public ResponseEntity<ZonaConsumoDTO> obtenerReporteZonas() {
        return ResponseEntity.ok(
                serviceReporteZonas.generarReporte()
        );
    }
}