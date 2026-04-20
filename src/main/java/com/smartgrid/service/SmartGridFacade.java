package com.smartgrid.service;

import org.springframework.stereotype.Service;

import com.smartgrid.dto.ResumenDashboardResponse;

@Service
public class SmartGridFacade {
	
	private final ServiceUsuario serviceUsuario;
    private final ServiceConsumoEnergetico serviceConsumoEnergetico;
    private final ServiceFacturaEnergetica serviceFacturaEnergetica;
    private final TarifaService tarifaService;
    private final ServiceCompositeEnergia serviceCompositeEnergia;

    public SmartGridFacade(ServiceUsuario serviceUsuario,
                           ServiceConsumoEnergetico serviceConsumoEnergetico,
                           ServiceFacturaEnergetica serviceFacturaEnergetica,
                           TarifaService tarifaService,
                           ServiceCompositeEnergia serviceCompositeEnergia) {

        this.serviceUsuario = serviceUsuario;
        this.serviceConsumoEnergetico = serviceConsumoEnergetico;
        this.serviceFacturaEnergetica = serviceFacturaEnergetica;
        this.tarifaService = tarifaService;
        this.serviceCompositeEnergia = serviceCompositeEnergia;
    }

    public ResumenDashboardResponse obtenerResumenDashboard() {

        ResumenDashboardResponse response = new ResumenDashboardResponse();

        response.setTotalUsuarios(serviceUsuario.contarUsuarios());
        response.setTotalConsumos(serviceConsumoEnergetico.contarConsumos());
        response.setTotalFacturas(serviceFacturaEnergetica.contarFacturas());
        response.setTarifas(tarifaService.listarTarifas());
        response.setConsumoTotalRed(serviceCompositeEnergia.obtenerConsumoTotalRed());

        return response;
    }

}
