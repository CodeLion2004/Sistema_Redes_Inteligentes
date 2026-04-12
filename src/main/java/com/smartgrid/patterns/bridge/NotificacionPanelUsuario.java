package com.smartgrid.patterns.bridge;

public class NotificacionPanelUsuario implements CanalNotificacion{

	@Override
    public String enviar(String mensaje) {
        return "Panel Usuario: " + mensaje;
    }
	
	@Override
    public String obtenerNombreCanal() {
        return "PANEL_USUARIO";
    }
	
	
}
