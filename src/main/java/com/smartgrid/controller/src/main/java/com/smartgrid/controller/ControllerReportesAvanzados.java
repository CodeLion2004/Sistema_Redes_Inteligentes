package com.smartgrid.controller;

import com.smartgrid.dto.ReporteAlertasZonaDTO;
import com.smartgrid.dto.ReporteFacturasEstadoDTO;
import com.smartgrid.service.ServiceReporteAlertasZona;
import com.smartgrid.service.ServiceReporteFacturasEstado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes-avanzados")
@CrossOrigin(origins = "*")
public class ControllerReportesAvanzados {

    private final ServiceReporteFacturasEstado serviceReporteFacturasEstado;
    private final ServiceReporteAlertasZona serviceReporteAlertasZona;
 

    public ControllerReportesAvanzados(ServiceReporteFacturasEstado serviceReporteFacturasEstado,
	            ServiceReporteAlertasZona serviceReporteAlertasZona) {
	this.serviceReporteFacturasEstado = serviceReporteFacturasEstado;
	this.serviceReporteAlertasZona = serviceReporteAlertasZona;

	}

    @GetMapping("/facturas-estado")
    public ResponseEntity<ReporteFacturasEstadoDTO> obtenerReporteFacturasEstado(
            @RequestParam(defaultValue = "semana") String periodo,
            @RequestParam(required = false) String rolUsuario
    ) {
        return ResponseEntity.ok(
                serviceReporteFacturasEstado.generarReporte(periodo, rolUsuario)
        );
    }

    @GetMapping("/alertas-zona")
    public ResponseEntity<ReporteAlertasZonaDTO> obtenerReporteAlertasZona(
            @RequestParam(defaultValue = "semana") String periodo,
            @RequestParam(required = false) String rolUsuario
    ) {
        return ResponseEntity.ok(
                serviceReporteAlertasZona.generarReporte(periodo, rolUsuario)
        );
    }
    
}