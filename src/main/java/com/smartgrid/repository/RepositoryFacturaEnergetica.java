package com.smartgrid.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.smartgrid.model.FacturaEnergetica;

public interface RepositoryFacturaEnergetica extends MongoRepository<FacturaEnergetica, String> {
    
    List<FacturaEnergetica> findByIdUsuario(String idUsuario);
}