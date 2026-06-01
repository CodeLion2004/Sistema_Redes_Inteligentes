package com.smartgrid.patterns.state;

import com.smartgrid.model.FacturaEnergetica;

public class EstadoVencida implements EstadoFactura {

    @Override
    public void marcarComoPagada(FacturaEnergetica factura) {
        factura.setEstado("PAGADA");
    }

    @Override
    public void marcarComoVencida(FacturaEnergetica factura) {
        throw new IllegalStateException("La factura ya está vencida.");
    }

    @Override
    public void anular(FacturaEnergetica factura) {
        factura.setEstado("ANULADA");
    }

    @Override
    public String getNombreEstado() {
        return "VENCIDA";
    }
}