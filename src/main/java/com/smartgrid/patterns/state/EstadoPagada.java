package com.smartgrid.patterns.state;

import com.smartgrid.model.FacturaEnergetica;

public class EstadoPagada implements EstadoFactura {

    @Override
    public void marcarComoPagada(FacturaEnergetica factura) {
        throw new IllegalStateException("La factura ya está pagada.");
    }

    @Override
    public void marcarComoVencida(FacturaEnergetica factura) {
        throw new IllegalStateException("Una factura pagada no puede marcarse como vencida.");
    }

    @Override
    public void anular(FacturaEnergetica factura) {
        throw new IllegalStateException("Una factura pagada no puede anularse.");
    }

    @Override
    public String getNombreEstado() {
        return "PAGADA";
    }
}