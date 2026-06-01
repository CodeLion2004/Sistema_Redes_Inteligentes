package com.smartgrid.patterns.command;

import com.smartgrid.model.ConfiguracionRed;


public class ComandoActualizarLimiteConsumo implements ComandoRed {

    private final ConfiguracionRed configuracion;

    private final double nuevoLimite;

    private double limiteAnterior;

    public ComandoActualizarLimiteConsumo(ConfiguracionRed configuracion, double nuevoLimite) {
        this.configuracion = configuracion;
        this.nuevoLimite = nuevoLimite;
    }

    @Override
    public void ejecutar() {
        this.limiteAnterior = configuracion.getLimiteConsumo();
        configuracion.setLimiteConsumo(nuevoLimite);
        System.out.println("[Command] Límite de consumo actualizado: "
                + limiteAnterior + " → " + nuevoLimite
                + " kWh | Config: " + configuracion.getNombrePlantilla());
    }

    @Override
    public void deshacer() {
        configuracion.setLimiteConsumo(limiteAnterior);
        System.out.println("[Command - UNDO] Límite de consumo revertido a: "
                + limiteAnterior + " kWh | Config: " + configuracion.getNombrePlantilla());
    }

    @Override
    public String getNombre() {
        return "ActualizarLimiteConsumo(" + configuracion.getNombrePlantilla()
                + ", " + nuevoLimite + " kWh)";
    }
}