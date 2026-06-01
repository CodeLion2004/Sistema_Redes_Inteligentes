package com.smartgrid.patterns.state;

import com.smartgrid.model.FacturaEnergetica;

public class EstadoAnulada implements EstadoFactura {

    @Override
    public void marcarComoPagada(FacturaEnergetica factura) {
        throw new IllegalStateException("Una factura anulada no puede pagarse.");
    }

    @Override
    public void marcarComoVencida(FacturaEnergetica factura) {
        throw new IllegalStateException("Una factura anulada no puede marcarse como vencida.");
    }

    @Override
    public void anular(FacturaEnergetica factura) {
        throw new IllegalStateException("La factura ya está anulada.");
    }

    @Override
    public String getNombreEstado() {
        return "ANULADA";
    }
}