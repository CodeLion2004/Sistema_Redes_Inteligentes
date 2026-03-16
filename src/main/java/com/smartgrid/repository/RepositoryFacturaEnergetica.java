package com.smartgrid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartgrid.model.FacturaEnergetica;

public interface RepositoryFacturaEnergetica extends MongoRepository<FacturaEnergetica, String>{

}
