package com.smartgrid.patterns.decorator;

public class DescuentoEnergiaRenovableDecorator extends FacturaDecorator{
	
	public DescuentoEnergiaRenovableDecorator(ComponenteFactura componenteFactura) {
        super(componenteFactura);
    }
	
	@Override
    public double calcularTotal() {
        return componenteFactura.calcularTotal() - 5.0;
    }

    @Override
    public String getDescripcion() {
        return componenteFactura.getDescripcion() + " + descuento energía renovable";
    }

}
