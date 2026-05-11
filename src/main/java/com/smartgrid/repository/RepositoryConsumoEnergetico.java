package com.smartgrid.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartgrid.model.ConsumoEnergetico;

public interface RepositoryConsumoEnergetico extends MongoRepository<ConsumoEnergetico, String>  {
	
	 List<ConsumoEnergetico> findByMarcaTiempoBetween(LocalDateTime from, LocalDateTime to);
}
