package com.smartgrid.patterns.state;

import com.smartgrid.model.FacturaEnergetica;

public interface EstadoFactura {

    void marcarComoPagada(FacturaEnergetica factura);

    void marcarComoVencida(FacturaEnergetica factura);

    void anular(FacturaEnergetica factura);

    String getNombreEstado();
}