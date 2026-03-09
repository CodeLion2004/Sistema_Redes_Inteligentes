package com.smartgrid.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartgrid.model.ConsumoEnergetico;

public interface RepositoryConsumoEnergetico extends MongoRepository<ConsumoEnergetico, String> {
}

