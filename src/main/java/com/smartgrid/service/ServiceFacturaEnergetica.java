package com.smartgrid.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartgrid.dto.FacturaRequest;
import com.smartgrid.model.FacturaEnergetica;
import com.smartgrid.patterns.decorator.CargoFijoDecorator;
import com.smartgrid.patterns.decorator.ComponenteFactura;
import com.smartgrid.patterns.decorator.DescuentoEnergiaRenovableDecorator;
import com.smartgrid.patterns.decorator.FacturaBase;
import com.smartgrid.patterns.decorator.ImpuestoDecorator;
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

        ComponenteFactura facturaDecorada = new FacturaBase(subtotal);
        facturaDecorada = new ImpuestoDecorator(facturaDecorada);
        facturaDecorada = new CargoFijoDecorator(facturaDecorada);

        double descuento = 0.0;

        if ("SOLAR".equalsIgnoreCase(request.getFuenteEnergia())
                || "EOLICA".equalsIgnoreCase(request.getFuenteEnergia())) {
            facturaDecorada = new DescuentoEnergiaRenovableDecorator(facturaDecorada);
            descuento = 5.0;
        }

        double total = facturaDecorada.calcularTotal();
        double impuestos = subtotal * 0.19;

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