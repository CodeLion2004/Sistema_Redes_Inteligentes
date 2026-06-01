package com.smartgrid.patterns.bridge;

public interface CanalNotificacion {
	
	String enviar(String mensaje);

    String obtenerNombreCanal();

}
