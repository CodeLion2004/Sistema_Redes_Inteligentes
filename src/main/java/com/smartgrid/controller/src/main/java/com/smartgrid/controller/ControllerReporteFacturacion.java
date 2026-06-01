package com.smartgrid.controller;

import com.smartgrid.dto.ReporteFacturacionDTO;
import com.smartgrid.service.ServiceReporteFacturacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ControllerReporteFacturacion {

    private final ServiceReporteFacturacion serviceReporteFacturacion;

    public ControllerReporteFacturacion(
            ServiceReporteFacturacion serviceReporteFacturacion
    ) {
        this.serviceReporteFacturacion = serviceReporteFacturacion;
    }

    @GetMapping("/facturacion-ingresos")
    public ResponseEntity<ReporteFacturacionDTO> obtenerReporteFacturacion(
            @RequestParam(defaultValue = "semana") String periodo
    ) {
        return ResponseEntity.ok(
                serviceReporteFacturacion.generarReporte(periodo)
        );
    }
}