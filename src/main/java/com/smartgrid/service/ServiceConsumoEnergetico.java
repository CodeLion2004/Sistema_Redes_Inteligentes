package com.smartgrid.service;
import org.springframework.stereotype.Service;

import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.repository.RepositoryConsumoEnergetico;

import java.util.List;

@Service
public class ServiceConsumoEnergetico {
	
	private final RepositoryConsumoEnergetico repository;

	public ServiceConsumoEnergetico(RepositoryConsumoEnergetico repository) {
		this.repository = repository;
	}
	
	public ConsumoEnergetico save(ConsumoEnergetico consumo) {
        return repository.save(consumo);
    }
	
	  public List<ConsumoEnergetico> findAll() {
	        return repository.findAll();
	    }
	  
	  public long contarConsumos() {
		    return repository.count();
		}  
	
}
