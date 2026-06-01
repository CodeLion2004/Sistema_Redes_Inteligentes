package com.smartgrid.dto;

import java.time.LocalDateTime;

public class AlertaResponse {
	
	private String tipoAlerta;
    private String mensaje;
    private String canal;
    private String nivel;
    private LocalDateTime fecha;
    
    public AlertaResponse() {
    }
    
    
    public AlertaResponse(String tipoAlerta, String mensaje, String canal, String nivel, LocalDateTime fecha) {
        this.tipoAlerta = tipoAlerta;
        this.mensaje = mensaje;
        this.canal = canal;
        this.nivel = nivel;
        this.fecha = fecha;
    }


	public String getTipoAlerta() {
		return tipoAlerta;
	}


	public void setTipoAlerta(String tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public String getCanal() {
		return canal;
	}


	public void setCanal(String canal) {
		this.canal = canal;
	}


	public String getNivel() {
		return nivel;
	}


	public void setNivel(String nivel) {
		this.nivel = nivel;
	}


	public LocalDateTime getFecha() {
		return fecha;
	}


	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
    
    
    

}
