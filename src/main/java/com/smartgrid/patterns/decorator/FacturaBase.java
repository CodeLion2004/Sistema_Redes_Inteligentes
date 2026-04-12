package com.smartgrid.patterns.decorator;

public class FacturaBase implements ComponenteFactura {
	
	private final double subtotal;
	
	public FacturaBase(double subtotal) {
        this.subtotal = subtotal;
    }
	
	@Override
    public double calcularTotal() {
        return subtotal;
    }

    @Override
    public String getDescripcion() {
        return "Factura base";
    }

}
