package com.smartgrid.patterns.adapter;

import java.time.LocalDateTime;

import com.smartgrid.dto.MedidorExternoDTO;
import com.smartgrid.model.ConsumoEnergetico;

public class MedidorExternoAdapter implements ConsumoEnergeticoAdapter<MedidorExternoDTO> {
	
	 	@Override
	    public ConsumoEnergetico adaptar(MedidorExternoDTO origen) {
	        ConsumoEnergetico consumo = new ConsumoEnergetico();
	        consumo.setIdDispositivo(origen.getDevice_id());
	        consumo.setConsumo(origen.getPower_kw());
	        consumo.setFuenteEnergia(origen.getSource());
	        consumo.setMarcaTiempo(LocalDateTime.parse(origen.getTimestamp()));
	        return consumo;
	    }

}
