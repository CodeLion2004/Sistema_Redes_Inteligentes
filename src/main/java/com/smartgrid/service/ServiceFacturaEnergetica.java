package com.smartgrid.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartgrid.dto.FacturaRequest;
import com.smartgrid.model.FacturaEnergetica;
import com.smartgrid.patterns.factorymethod.EstrategiaFacturacion;
import com.smartgrid.patterns.factorymethod.FacturacionFactory;
import com.smartgrid.patterns.singleton.ConfiguracionTarifas;
import com.smartgrid.repository.RepositoryFacturaEnergetica;


@Service
public class ServiceFacturaEnergetica {
	
	private final RepositoryFacturaEnergetica facturaEnergeticaRepository;

	public ServiceFacturaEnergetica(RepositoryFacturaEnergetica facturaEnergeticaRepository) {
		this.facturaEnergeticaRepository = facturaEnergeticaRepository;
	}
	
	public FacturaEnergetica generarFactura(FacturaRequest request) {
        double tarifaBase = ConfiguracionTarifas.INSTANCIA.obtenerTarifaBase(request.getTipoUsuario());

        EstrategiaFacturacion estrategia =
                FacturacionFactory.crearEstrategia(request.getTipoFacturacion());

        double subtotal = estrategia.calcularFactura(request.getConsumo(), tarifaBase);
        double impuestos = subtotal * 0.19;
        double descuento = subtotal > 100 ? subtotal * 0.05 : 0;
        double total = subtotal + impuestos - descuento;

        FacturaEnergetica factura = new FacturaEnergetica.Builder()
                .idUsuario(request.getIdUsuario())
                .nombreUsuario(request.getNombreUsuario())
                .consumo(request.getConsumo())
                .tarifaBase(tarifaBase)
                .tipoFacturacion(request.getTipoFacturacion())
                .subtotal(subtotal)
                .impuestos(impuestos)
                .descuento(descuento)
                .total(total)
                .fuenteEnergia(request.getFuenteEnergia())
                .fechaEmision(LocalDateTime.now())
                .build();

        return facturaEnergeticaRepository.save(factura);
    }

    public List<FacturaEnergetica> listarFacturas() {
        return facturaEnergeticaRepository.findAll();
    }
	
	
	

}
