package com.smartgrid.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.smartgrid.model.Usuario;
import com.smartgrid.service.ServiceUsuario;


@RestController
@RequestMapping("/usuarios")
public class ControllerUser {

	private final ServiceUsuario serviceUsuario;

	public ControllerUser(ServiceUsuario serviceUsuario) {
		this.serviceUsuario = serviceUsuario;
	}
	
	 @PostMapping
	 public Usuario guardarUsuario(@RequestBody Usuario usuario) {
		 return serviceUsuario.guardarUsuario(usuario);
	 }
	
	
	@GetMapping
	public List<Usuario> listarUsuarios(){
		return serviceUsuario.ListarUsuarios();
	}
	
	
	
	@GetMapping("/correo/{correo}")
	public Optional<Usuario>buscarPorCorreo(@PathVariable String correo){
		return serviceUsuario.buscarPorCorreo(correo);
	}
	
	
}
