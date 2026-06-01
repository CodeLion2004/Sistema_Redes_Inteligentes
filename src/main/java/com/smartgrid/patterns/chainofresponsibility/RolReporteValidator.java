package com.smartgrid.patterns.chainofresponsibility;

public class RolReporteValidator extends ReporteValidator {

    @Override
    protected void validarActual(ReporteRequest request) {
        String rol = request.getRolUsuario();

        if (rol == null || rol.isBlank()) {
            return;
        }

        if (!"ADMIN".equalsIgnoreCase(rol) && !"USER".equalsIgnoreCase(rol)) {
            throw new IllegalArgumentException("Rol no permitido para generar reportes: " + rol);
        }
    }
}