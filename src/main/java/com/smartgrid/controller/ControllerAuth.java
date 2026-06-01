package com.smartgrid.controller;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartgrid.model.Usuario;
import com.smartgrid.repository.RepositoryUsuario;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerAuth {

    @Autowired
    private RepositoryUsuario usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        String correo = request.getUsername();
        String password = request.getPassword();
        String rolFrontend = request.getRol();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuario no encontrado"));
        }

        Usuario usuario = usuarioOpt.get();

        if (!Objects.equals(usuario.getContrasena(), password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Contraseña incorrecta"));
        }

        String rolBD = usuario.getRol() != null
                ? usuario.getRol().toUpperCase()
                : "";

        if ("admin".equals(rolFrontend) && !"ADMIN".equals(rolBD)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "No tienes permisos de administrador"));
        }

        if ("usuario".equals(rolFrontend) && !"USER".equals(rolBD)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "No tienes permisos de usuario"));
        }

        String token = generarToken(usuario.getCorreo(), rolFrontend);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "rol", rolFrontend
        ));
    }

    private String generarToken(String correo, String rol) {
        return correo + ":" + rol + ":" + System.currentTimeMillis();
    }
}

class LoginRequest {
    private String username;
    private String password;
    private String rol;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}