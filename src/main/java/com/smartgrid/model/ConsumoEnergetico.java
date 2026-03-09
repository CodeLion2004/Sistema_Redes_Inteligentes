package com.smartgrid.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "energy_consumption")
public class ConsumoEnergetico {

	
	  	@Id
	    private String id;

	    private String idDispositivo;

	    private double consumo;

	    private String fuenteEnergia;

	    private LocalDateTime marcaTiempo;

		public ConsumoEnergetico() {
		}

		public ConsumoEnergetico(String idDispositivo, double consumo, String fuenteEnergia,
				LocalDateTime marcaTiempo) {
			super();
			this.idDispositivo = idDispositivo;
			this.consumo = consumo;
			this.fuenteEnergia = fuenteEnergia;
			this.marcaTiempo = marcaTiempo;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIdDispositivo() {
			return idDispositivo;
		}

		public void setIdDispositivo(String idDispositivo) {
			this.idDispositivo = idDispositivo;
		}

		public double getConsumo() {
			return consumo;
		}

		public void setConsumo(double consumo) {
			this.consumo = consumo;
		}

		public String getFuenteEnergia() {
			return fuenteEnergia;
		}

		public void setFuenteEnergia(String fuenteEnergia) {
			this.fuenteEnergia = fuenteEnergia;
		}

		public LocalDateTime getMarcaTiempo() {
			return marcaTiempo;
		}

		public void setMarcaTiempo(LocalDateTime marcaTiempo) {
			this.marcaTiempo = marcaTiempo;
		}
	    
	    
	    
}
