package com.smartgrid.controller;

import com.smartgrid.dto.ReporteAlertasZonaDTO;
import com.smartgrid.dto.ReporteCicloVidaFacturaDTO;
import com.smartgrid.dto.ReporteFuenteEnergiaDTO;
import com.smartgrid.dto.ReporteFacturasEstadoDTO;
import com.smartgrid.service.ServiceReporteAlertasZona;
import com.smartgrid.service.ServiceReporteCicloVidaFactura;
import com.smartgrid.service.ServiceReporteFuenteEnergia;
import com.smartgrid.service.ServiceReporteFacturasEstado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes-avanzados")
@CrossOrigin(origins = "*")
public class ControllerReportesAvanzados {

    private final ServiceReporteFacturasEstado   serviceReporteFacturasEstado;
    private final ServiceReporteAlertasZona      serviceReporteAlertasZona;
    private final ServiceReporteFuenteEnergia    serviceReporteFuenteEnergia;
    private final ServiceReporteCicloVidaFactura serviceReporteCicloVida;

    public ControllerReportesAvanzados(
            ServiceReporteFacturasEstado   serviceReporteFacturasEstado,
            ServiceReporteAlertasZona      serviceReporteAlertasZona,
            ServiceReporteFuenteEnergia    serviceReporteFuenteEnergia,
            ServiceReporteCicloVidaFactura serviceReporteCicloVida) {
        this.serviceReporteFacturasEstado = serviceReporteFacturasEstado;
        this.serviceReporteAlertasZona    = serviceReporteAlertasZona;
        this.serviceReporteFuenteEnergia  = serviceReporteFuenteEnergia;
        this.serviceReporteCicloVida      = serviceReporteCicloVida;
    }

    @GetMapping("/facturas-estado")
    public ResponseEntity<ReporteFacturasEstadoDTO> obtenerReporteFacturasEstado(
            @RequestParam(defaultValue = "semana") String periodo,
            @RequestParam(required = false) String rolUsuario) {
        return ResponseEntity.ok(
                serviceReporteFacturasEstado.generarReporte(periodo, rolUsuario));
    }

    @GetMapping("/alertas-zona")
    public ResponseEntity<ReporteAlertasZonaDTO> obtenerReporteAlertasZona(
            @RequestParam(defaultValue = "semana") String periodo,
            @RequestParam(required = false) String rolUsuario) {
        return ResponseEntity.ok(
                serviceReporteAlertasZona.generarReporte(periodo, rolUsuario));
    }

    @GetMapping("/fuente-energia")
    public ResponseEntity<ReporteFuenteEnergiaDTO> obtenerReporteFuenteEnergia(
            @RequestParam(defaultValue = "semana") String periodo,
            @RequestParam(required = false) String rolUsuario) {
        return ResponseEntity.ok(
                serviceReporteFuenteEnergia.generarReporte(periodo, rolUsuario));
    }

    /* ── NUEVO ENDPOINT ── */
    @GetMapping("/ciclo-vida-factura")
    public ResponseEntity<ReporteCicloVidaFacturaDTO> obtenerReporteCicloVida(
            @RequestParam(defaultValue = "semana") String periodo,
            @RequestParam(required = false) String rolUsuario) {
        return ResponseEntity.ok(
                serviceReporteCicloVida.generarReporte(periodo, rolUsuario));
    }
}