package com.smartgrid.patterns.state;

import com.smartgrid.model.FacturaEnergetica;

public class FacturaEstadoContext {

    private EstadoFactura estadoFactura;

    public FacturaEstadoContext(String estadoActual) {
        this.estadoFactura = resolverEstado(estadoActual);
    }

    public void marcarComoPagada(FacturaEnergetica factura) {
        estadoFactura.marcarComoPagada(factura);
    }

    public void marcarComoVencida(FacturaEnergetica factura) {
        estadoFactura.marcarComoVencida(factura);
    }

    public void anular(FacturaEnergetica factura) {
        estadoFactura.anular(factura);
    }

    private EstadoFactura resolverEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            return new EstadoPendiente();
        }

        return switch (estado.toUpperCase()) {
            case "PAGADA" -> new EstadoPagada();
            case "VENCIDA" -> new EstadoVencida();
            case "ANULADA" -> new EstadoAnulada();
            default -> new EstadoPendiente();
        };
    }
}