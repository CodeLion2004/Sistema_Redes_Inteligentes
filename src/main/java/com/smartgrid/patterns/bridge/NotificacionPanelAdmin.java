package com.smartgrid.patterns.bridge;

public class NotificacionPanelAdmin implements CanalNotificacion{

	@Override
	public String enviar(String mensaje) {
        return "Panel Admin: " + mensaje;
    }

    @Override
    public String obtenerNombreCanal() {
        return "PANEL_ADMIN";
    }
}
