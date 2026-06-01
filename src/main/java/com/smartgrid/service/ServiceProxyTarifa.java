package com.smartgrid.service;

import org.springframework.stereotype.Service;

import com.smartgrid.patterns.proxy.ProxyTarifaAdmin;

@Service
public class ServiceProxyTarifa {

	private final ServiceUsuario serviceUsuario;
    private final TarifaService tarifaService;

    public ServiceProxyTarifa(ServiceUsuario serviceUsuario, TarifaService tarifaService) {
        this.serviceUsuario = serviceUsuario;
        this.tarifaService = tarifaService;
    }

    public String actualizarTarifaConProxy(String correoUsuario, String tipoUsuario, double nuevaTarifa) {
        ProxyTarifaAdmin proxy = new ProxyTarifaAdmin(serviceUsuario, tarifaService);
        return proxy.actualizarTarifa(correoUsuario, tipoUsuario, nuevaTarifa);
    }
	
}
