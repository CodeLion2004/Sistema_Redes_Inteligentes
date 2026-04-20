package com.smartgrid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartgrid.model.Usuario;
import com.smartgrid.repository.RepositoryUsuario;

@Service
public class ServiceUsuario {
	
	private final RepositoryUsuario usuarioRepository;

	public ServiceUsuario(RepositoryUsuario usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public Usuario guardarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	
	public List<Usuario> ListarUsuarios(){
		return usuarioRepository.findAll();
	}
	
	
	public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
	
	public long contarUsuarios() {
	    return usuarioRepository.count();
	}
}

