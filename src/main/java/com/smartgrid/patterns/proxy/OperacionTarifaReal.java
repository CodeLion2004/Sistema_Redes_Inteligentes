package com.smartgrid.patterns.proxy;

import com.smartgrid.service.TarifaService;

public class OperacionTarifaReal  implements OperacionTarifa {
	
	private final TarifaService tarifaService;

    public OperacionTarifaReal(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @Override
    public String actualizarTarifa(String correoUsuario, String tipoUsuario, double nuevaTarifa) {
        tarifaService.actualizarTarifa(tipoUsuario, nuevaTarifa);
        return "Tarifa actualizada correctamente por " + correoUsuario;
    }

}
