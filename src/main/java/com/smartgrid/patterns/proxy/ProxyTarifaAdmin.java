package com.smartgrid.patterns.proxy;


import java.util.Optional;

import com.smartgrid.model.Usuario;
import com.smartgrid.service.ServiceUsuario;
import com.smartgrid.service.TarifaService;

public class ProxyTarifaAdmin implements OperacionTarifa{
	
	private final ServiceUsuario serviceUsuario;
    private final OperacionTarifaReal operacionTarifaReal;

    public ProxyTarifaAdmin(ServiceUsuario serviceUsuario, TarifaService tarifaService) {
        this.serviceUsuario = serviceUsuario;
        this.operacionTarifaReal = new OperacionTarifaReal(tarifaService);
    }

    @Override
    public String actualizarTarifa(String correoUsuario, String tipoUsuario, double nuevaTarifa) {

        Optional<Usuario> usuarioOptional = serviceUsuario.buscarPorCorreo(correoUsuario);

        if (usuarioOptional.isEmpty()) {
            return "Acceso denegado: usuario no encontrado";
        }

        Usuario usuario = usuarioOptional.get();

        if (usuario.getRol() == null || !"ADMIN".equalsIgnoreCase(String.valueOf(usuario.getRol()))) {
            return "Acceso denegado: solo un ADMIN puede actualizar tarifas";
        }

        return operacionTarifaReal.actualizarTarifa(correoUsuario, tipoUsuario, nuevaTarifa);
    }


}
