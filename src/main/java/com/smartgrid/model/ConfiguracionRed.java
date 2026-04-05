package com.smartgrid.model;

public class ConfiguracionRed implements Cloneable{
	
	private String nombrePlantilla;
    private double limiteConsumo;
    private String prioridadEnergia;
    private String fuentePreferida;
    private String tipoFacturacion;
    private String nivelAlerta;

    public ConfiguracionRed() {
    }

	public ConfiguracionRed(String nombrePlantilla, double limiteConsumo, String prioridadEnergia,
			String fuentePreferida, String tipoFacturacion, String nivelAlerta) {
		this.nombrePlantilla = nombrePlantilla;
		this.limiteConsumo = limiteConsumo;
		this.prioridadEnergia = prioridadEnergia;
		this.fuentePreferida = fuentePreferida;
		this.tipoFacturacion = tipoFacturacion;
		this.nivelAlerta = nivelAlerta;
	}
    
    
	 @Override
	    public ConfiguracionRed clone() {
	        try {
	            return (ConfiguracionRed) super.clone();
	        } catch (CloneNotSupportedException e) {
	            throw new RuntimeException("Error al clonar la configuración", e);
	        }
	    }

	public String getNombrePlantilla() {
		return nombrePlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	public double getLimiteConsumo() {
		return limiteConsumo;
	}

	public void setLimiteConsumo(double limiteConsumo) {
		this.limiteConsumo = limiteConsumo;
	}

	public String getPrioridadEnergia() {
		return prioridadEnergia;
	}

	public void setPrioridadEnergia(String prioridadEnergia) {
		this.prioridadEnergia = prioridadEnergia;
	}

	public String getFuentePreferida() {
		return fuentePreferida;
	}

	public void setFuentePreferida(String fuentePreferida) {
		this.fuentePreferida = fuentePreferida;
	}

	public String getTipoFacturacion() {
		return tipoFacturacion;
	}

	public void setTipoFacturacion(String tipoFacturacion) {
		this.tipoFacturacion = tipoFacturacion;
	}

	public String getNivelAlerta() {
		return nivelAlerta;
	}

	public void setNivelAlerta(String nivelAlerta) {
		this.nivelAlerta = nivelAlerta;
	}
	 
	 
	 

}
