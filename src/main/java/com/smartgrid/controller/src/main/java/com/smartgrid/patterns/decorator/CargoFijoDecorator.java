package com.smartgrid.patterns.decorator;

public class CargoFijoDecorator extends FacturaDecorator{
	
	public CargoFijoDecorator(ComponenteFactura componenteFactura) {
        super(componenteFactura);
    }

    @Override
    public double calcularTotal() {
        return componenteFactura.calcularTotal() + 3.0;
    }

    @Override
    public String getDescripcion() {
        return componenteFactura.getDescripcion() + " + cargo fijo";
    }

}
