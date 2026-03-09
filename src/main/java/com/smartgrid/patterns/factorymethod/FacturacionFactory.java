package com.smartgrid.patterns.factorymethod;

public class FacturacionFactory {
	
	 private static final String PAQUETE = "com.smartgrid.patterns.factorymethod.";

	    public static EstrategiaFacturacion crearEstrategia(String tipo) {
	        try {
	            String nombreClase = PAQUETE + tipo;

	            Class<?> clase = Class.forName(nombreClase);

	            Object instancia = clase.getDeclaredConstructor().newInstance();

	            return (EstrategiaFacturacion) instancia;

	        } catch (Exception e) {
	            throw new RuntimeException("Error al crear la estrategia de facturación: " + tipo, e);
	        }
	    }

}
