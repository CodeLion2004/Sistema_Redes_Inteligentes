package com.smartgrid.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartgrid.model.Usuario;

public interface RepositoryUsuario extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByCorreo(String correo);
}