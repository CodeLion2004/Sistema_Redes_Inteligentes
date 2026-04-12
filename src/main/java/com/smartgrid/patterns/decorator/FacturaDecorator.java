package com.smartgrid.patterns.decorator;

public abstract class FacturaDecorator implements ComponenteFactura{
	
	protected final ComponenteFactura componenteFactura;

    public FacturaDecorator(ComponenteFactura componenteFactura) {
        this.componenteFactura = componenteFactura;
    }

}
