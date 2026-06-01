package com.smartgrid.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartgrid.dto.FacturaRequest;
import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.model.FacturaEnergetica;
import com.smartgrid.patterns.decorator.CargoFijoDecorator;
import com.smartgrid.patterns.decorator.ComponenteFactura;
import com.smartgrid.patterns.decorator.DescuentoEnergiaRenovableDecorator;
import com.smartgrid.patterns.decorator.FacturaBase;
import com.smartgrid.patterns.decorator.ImpuestoDecorator;
import com.smartgrid.patterns.factorymethod.EstrategiaFacturacion;
import com.smartgrid.patterns.factorymethod.FacturacionFactory;
import com.smartgrid.patterns.singleton.ConfiguracionTarifas;
import com.smartgrid.patterns.state.FacturaEstadoContext;
import com.smartgrid.repository.RepositoryConsumoEnergetico;
import com.smartgrid.repository.RepositoryFacturaEnergetica;
import com.smartgrid.repository.RepositoryUsuario;

@Service
public class ServiceFacturaEnergetica {

    private final RepositoryFacturaEnergetica facturaEnergeticaRepository;
    private final RepositoryUsuario usuarioRepository;
    private final RepositoryConsumoEnergetico consumoRepository;

    public ServiceFacturaEnergetica(
            RepositoryFacturaEnergetica facturaEnergeticaRepository,
            RepositoryUsuario usuarioRepository,
            RepositoryConsumoEnergetico consumoRepository
    ) {
        this.facturaEnergeticaRepository = facturaEnergeticaRepository;
        this.usuarioRepository = usuarioRepository;
        this.consumoRepository = consumoRepository;
    }

    public FacturaEnergetica generarFactura(FacturaRequest request) {

        if (request.getIdUsuario() == null || request.getIdUsuario().isBlank()) {
            throw new IllegalArgumentException("El idUsuario es obligatorio");
        }

        var usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        if (request.getConsumo() <= 0) {
            throw new IllegalArgumentException("El consumo debe ser mayor a cero");
        }

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
        LocalDateTime fechaActual = LocalDateTime.now();

        ConsumoEnergetico consumoGenerado = new ConsumoEnergetico(
                "FACT-" + request.getIdUsuario(),
                request.getIdUsuario(),
                request.getConsumo(),
                request.getFuenteEnergia(),
                fechaActual
        );

        ConsumoEnergetico consumoGuardado = consumoRepository.save(consumoGenerado);

        FacturaEnergetica factura = new FacturaEnergetica.Builder()
                .idUsuario(request.getIdUsuario())
                .nombreUsuario(usuario.getNombre())
                .zona(usuario.getZona())
                .idConsumo(consumoGuardado.getId())
                .consumo(request.getConsumo())
                .tarifaBase(tarifaBase)
                .tipoFacturacion(request.getTipoFacturacion())
                .subtotal(subtotal)
                .impuestos(impuestos)
                .descuento(descuento)
                .total(total)
                .fuenteEnergia(request.getFuenteEnergia())
                .estado("pendientes")
                .fechaEmision(fechaActual)
                .build();

        return facturaEnergeticaRepository.save(factura);
    }

    public List<FacturaEnergetica> listarFacturas() {
        return facturaEnergeticaRepository.findAll();
    }

    public long contarFacturas() {
        return facturaEnergeticaRepository.count();
    }

    public List<FacturaEnergetica> listarFacturasPorUsuario(String usuarioId) {
        return facturaEnergeticaRepository.findByIdUsuario(usuarioId);
    }

    public void eliminarFactura(String id) {
        facturaEnergeticaRepository.deleteById(id);
    }
    public FacturaEnergetica cambiarEstadoFactura(String idFactura, String accion) {

        FacturaEnergetica factura = facturaEnergeticaRepository.findById(idFactura)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        FacturaEstadoContext contexto = new FacturaEstadoContext(factura.getEstado());

        switch (accion.toUpperCase()) {
            case "PAGAR" -> contexto.marcarComoPagada(factura);
            case "VENCER" -> contexto.marcarComoVencida(factura);
            case "ANULAR" -> contexto.anular(factura);
            default -> throw new IllegalArgumentException("Acción de estado no válida");
        }

        return facturaEnergeticaRepository.save(factura);
    }
    
}