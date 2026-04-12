package com.smartgrid.patterns.bridge;

public abstract class AlertaEnergetica {

	protected CanalNotificacion canalNotificacion;

	public AlertaEnergetica(CanalNotificacion canalNotificacion) {
		this.canalNotificacion = canalNotificacion;
	}
	
	public abstract String generarMensaje();

    public String enviarAlerta() {
        return canalNotificacion.enviar(generarMensaje());
    }

    public String obtenerCanal() {
        return canalNotificacion.obtenerNombreCanal();
    }
	
}
