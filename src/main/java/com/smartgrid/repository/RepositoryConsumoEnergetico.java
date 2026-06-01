package com.smartgrid.repository;

import com.smartgrid.model.ConsumoEnergetico;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositoryConsumoEnergetico extends MongoRepository<ConsumoEnergetico, String> {
    List<ConsumoEnergetico> findByMarcaTiempoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}