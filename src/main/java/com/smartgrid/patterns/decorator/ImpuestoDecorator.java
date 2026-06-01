package com.smartgrid.patterns.decorator;

public class ImpuestoDecorator extends FacturaDecorator{
	
	public ImpuestoDecorator(ComponenteFactura componenteFactura) {
        super(componenteFactura);
    }
	
	@Override
    public double calcularTotal() {
        return componenteFactura.calcularTotal() * 1.19;
    }

    @Override
    public String getDescripcion() {
        return componenteFactura.getDescripcion() + " + impuesto";
    }


}
