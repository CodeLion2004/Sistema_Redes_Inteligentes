package com.smartgrid.patterns.state;

import com.smartgrid.model.FacturaEnergetica;

public class EstadoPendiente implements EstadoFactura {

    @Override
    public void marcarComoPagada(FacturaEnergetica factura) {
        factura.setEstado("PAGADA");
    }

    @Override
    public void marcarComoVencida(FacturaEnergetica factura) {
        factura.setEstado("VENCIDA");
    }

    @Override
    public void anular(FacturaEnergetica factura) {
        factura.setEstado("ANULADA");
    }

    @Override
    public String getNombreEstado() {
        return "PENDIENTE";
    }
}