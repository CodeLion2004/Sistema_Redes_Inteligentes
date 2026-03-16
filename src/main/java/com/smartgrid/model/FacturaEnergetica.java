package com.smartgrid.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facturas_energeticas")
public class FacturaEnergetica {
	
	 @Id
	 private String id;
	 private String idUsuario;
	 private String nombreUsuario;
	 private double consumo;
	 private double tarifaBase;
	 private String tipoFacturacion;
	 private double subtotal;
	 private double impuestos;
	 private double descuento;
	 private double total;
	 private String fuenteEnergia;
	 private LocalDateTime fechaEmision;

	 
	  public FacturaEnergetica() {
	  }


	  public FacturaEnergetica(Builder builder) {
		    this.id = builder.id;
		    this.idUsuario = builder.idUsuario;
		    this.nombreUsuario = builder.nombreUsuario;
		    this.consumo = builder.consumo;
		    this.tarifaBase = builder.tarifaBase;
		    this.tipoFacturacion = builder.tipoFacturacion;
		    this.subtotal = builder.subtotal;
		    this.impuestos = builder.impuestos;
		    this.descuento = builder.descuento;
		    this.total = builder.total;
		    this.fuenteEnergia = builder.fuenteEnergia;
		    this.fechaEmision = builder.fechaEmision;
		}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public double getTarifaBase() {
		return tarifaBase;
	}


	public void setTarifaBase(double tarifaBase) {
		this.tarifaBase = tarifaBase;
	}


	public String getTipoFacturacion() {
		return tipoFacturacion;
	}


	public void setTipoFacturacion(String tipoFacturacion) {
		this.tipoFacturacion = tipoFacturacion;
	}


	public double getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}


	public double getImpuestos() {
		return impuestos;
	}


	public void setImpuestos(double impuestos) {
		this.impuestos = impuestos;
	}


	public double getDescuento() {
		return descuento;
	}


	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public String getFuenteEnergia() {
		return fuenteEnergia;
	}


	public void setFuenteEnergia(String fuenteEnergia) {
		this.fuenteEnergia = fuenteEnergia;
	}


	public LocalDateTime getFechaEmision() {
		return fechaEmision;
	}


	public void setFechaEmision(LocalDateTime fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	
	
	public static class Builder {
        private String id;
        private String idUsuario;
        private String nombreUsuario;
        private double consumo;
        private double tarifaBase;
        private String tipoFacturacion;
        private double subtotal;
        private double impuestos;
        private double descuento;
        private double total;
        private String fuenteEnergia;
        private LocalDateTime fechaEmision;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder idUsuario(String idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public Builder nombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
            return this;
        }

        public Builder consumo(double consumo) {
            this.consumo = consumo;
            return this;
        }

        public Builder tarifaBase(double tarifaBase) {
            this.tarifaBase = tarifaBase;
            return this;
        }

        public Builder tipoFacturacion(String tipoFacturacion) {
            this.tipoFacturacion = tipoFacturacion;
            return this;
        }

        public Builder subtotal(double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder impuestos(double impuestos) {
            this.impuestos = impuestos;
            return this;
        }

        public Builder descuento(double descuento) {
            this.descuento = descuento;
            return this;
        }

        public Builder total(double total) {
            this.total = total;
            return this;
        }

        public Builder fuenteEnergia(String fuenteEnergia) {
            this.fuenteEnergia = fuenteEnergia;
            return this;
        }

        public Builder fechaEmision(LocalDateTime fechaEmision) {
            this.fechaEmision = fechaEmision;
            return this;
        }

        public FacturaEnergetica build() {
            return new FacturaEnergetica(this);
        }
    }
	
	  
	  
}
