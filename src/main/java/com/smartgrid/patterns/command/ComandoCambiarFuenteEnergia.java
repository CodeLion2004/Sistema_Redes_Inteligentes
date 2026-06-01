package com.smartgrid.patterns.command;

import com.smartgrid.model.ConfiguracionRed;


public class ComandoCambiarFuenteEnergia implements ComandoRed {

    private final ConfiguracionRed configuracion;
    private final String nuevaFuente;
    private String fuenteAnterior;

    public ComandoCambiarFuenteEnergia(ConfiguracionRed configuracion, String nuevaFuente) {
        this.configuracion = configuracion;
        this.nuevaFuente = nuevaFuente;
    }

    @Override
    public void ejecutar() {
        this.fuenteAnterior = configuracion.getFuentePreferida();
        configuracion.setFuentePreferida(nuevaFuente);
        System.out.println("[Command] Fuente de energía cambiada: "
                + fuenteAnterior + " → " + nuevaFuente
                + " | Config: " + configuracion.getNombrePlantilla());
    }

    @Override
    public void deshacer() {
        configuracion.setFuentePreferida(fuenteAnterior);
        System.out.println("[Command - UNDO] Fuente de energía revertida a: "
                + fuenteAnterior + " | Config: " + configuracion.getNombrePlantilla());
    }

    @Override
    public String getNombre() {
        return "CambiarFuenteEnergia(" + configuracion.getNombrePlantilla()
                + ", " + nuevaFuente + ")";
    }
}