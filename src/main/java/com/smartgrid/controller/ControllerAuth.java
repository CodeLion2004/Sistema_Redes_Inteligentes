package com.smartgrid.controller;

import java.util.Map;
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
        String correo = request.getUsername();      // el email ingresado en el login
        String password = request.getPassword();
        String rolFrontend = request.getRol();      // "admin" o "usuario"

        // Buscar usuario por correo
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuario no encontrado"));
        }

        Usuario usuario = usuarioOpt.get();

        // Validar contraseña (texto plano, según tu modelo)
        if (!usuario.getContrasena().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Contraseña incorrecta"));
        }

        // Validar rol: el rol en BD (por ejemplo "ADMIN" o "USER") debe coincidir con el seleccionado
        String rolBD = usuario.getRol().toUpperCase();  // "ADMIN" o "USER"
        if (rolFrontend.equals("admin") && !rolBD.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "No tienes permisos de administrador"));
        }
        if (rolFrontend.equals("usuario") && !rolBD.equals("USER")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "No tienes permisos de usuario"));
        }

        // Generar token simple (puedes mejorarlo después)
        String token = generarToken(usuario.getCorreo(), rolFrontend);
        return ResponseEntity.ok(Map.of("token", token, "rol", rolFrontend));
    }

    private String generarToken(String correo, String rol) {
        return correo + ":" + rol + ":" + System.currentTimeMillis();
    }
}

// Clase auxiliar para recibir el JSON del login
class LoginRequest {
    private String username;
    private String password;
    private String rol;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}