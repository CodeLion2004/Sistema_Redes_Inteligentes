package com.smartgrid.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.smartgrid.dto.AlertaResponse;
import com.smartgrid.patterns.bridge.*;

@Service
public class AlertaService {
	
	public AlertaResponse generarAlertaConsumoAltoAdmin(String idDispositivo, double consumo) {
        CanalNotificacion canal = new NotificacionPanelAdmin();
        AlertaEnergetica alerta = new AlertaConsumoAlto(canal, idDispositivo, consumo);
        
        return new AlertaResponse(
                "CONSUMO_ALTO",
                alerta.enviarAlerta(),
                alerta.obtenerCanal(),
                "ALTA",
                LocalDateTime.now()
        );
    }

	
	public AlertaResponse generarAlertaConsumoAltoUsuario(String idDispositivo, double consumo) {
        CanalNotificacion canal = new NotificacionPanelUsuario();
        AlertaEnergetica alerta = new AlertaConsumoAlto(canal, idDispositivo, consumo);

        return new AlertaResponse(
                "CONSUMO_ALTO",
                alerta.enviarAlerta(),
                alerta.obtenerCanal(),
                "MEDIA",
                LocalDateTime.now()
        );
    }
	
	
	
	public AlertaResponse generarAlertaTarifaAdmin(String tipoUsuario, double nuevaTarifa) {
        CanalNotificacion canal = new NotificacionPanelAdmin();
        AlertaEnergetica alerta = new AlertaTarifaActualizada(canal, tipoUsuario, nuevaTarifa);

        return new AlertaResponse(
                "TARIFA_ACTUALIZADA",
                alerta.enviarAlerta(),
                alerta.obtenerCanal(),
                "MEDIA",
                LocalDateTime.now()
        );
    }
	
	
	public AlertaResponse generarAlertaTarifaUsuario(String tipoUsuario, double nuevaTarifa) {
        CanalNotificacion canal = new NotificacionPanelUsuario();
        AlertaEnergetica alerta = new AlertaTarifaActualizada(canal, tipoUsuario, nuevaTarifa);

        return new AlertaResponse(
                "TARIFA_ACTUALIZADA",
                alerta.enviarAlerta(),
                alerta.obtenerCanal(),
                "BAJA",
                LocalDateTime.now()
        );
    }
	
}
