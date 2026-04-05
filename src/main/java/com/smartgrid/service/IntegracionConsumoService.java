package com.smartgrid.service;

import org.springframework.stereotype.Service;

import com.smartgrid.dto.MedidorExternoDTO;
import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.patterns.adapter.MedidorExternoAdapter;
import com.smartgrid.repository.RepositoryConsumoEnergetico;

@Service
public class IntegracionConsumoService {
	
	private final RepositoryConsumoEnergetico repository;
	
	public IntegracionConsumoService(RepositoryConsumoEnergetico repository) {
        this.repository = repository;
    }

	
	public ConsumoEnergetico guardarDesdeMedidorExterno(MedidorExternoDTO dto) {
        MedidorExternoAdapter adapter = new MedidorExternoAdapter();
        ConsumoEnergetico consumo = adapter.adaptar(dto);
        return repository.save(consumo);
    }
}
