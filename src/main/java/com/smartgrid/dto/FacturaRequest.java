package com.smartgrid.dto;

public class FacturaRequest {
	
	private String idUsuario;
    private String nombreUsuario;
    private double consumo;
    private String tipoFacturacion;
    private String tipoUsuario;
    private String fuenteEnergia;
    
    
	public FacturaRequest() {
	}


	public String getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public double getConsumo() {
		return consumo;
	}


	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}


	public String getTipoFacturacion() {
		return tipoFacturacion;
	}


	public void setTipoFacturacion(String tipoFacturacion) {
		this.tipoFacturacion = tipoFacturacion;
	}


	public String getTipoUsuario() {
		return tipoUsuario;
	}


	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}


	public String getFuenteEnergia() {
		return fuenteEnergia;
	}


	public void setFuenteEnergia(String fuenteEnergia) {
		this.fuenteEnergia = fuenteEnergia;
	}
	
	
    
    

}
