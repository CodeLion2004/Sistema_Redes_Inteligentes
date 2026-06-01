package com.smartgrid.patterns.command;

import com.smartgrid.model.ConfiguracionRed;


public class ComandoCambiarNivelAlerta implements ComandoRed {

    private final ConfiguracionRed configuracion;
    private final String nuevoNivel;
    private String nivelAnterior;

    public ComandoCambiarNivelAlerta(ConfiguracionRed configuracion, String nuevoNivel) {
        this.configuracion = configuracion;
        this.nuevoNivel = nuevoNivel;
    }

    @Override
    public void ejecutar() {
        this.nivelAnterior = configuracion.getNivelAlerta();
        configuracion.setNivelAlerta(nuevoNivel);
        System.out.println("[Command] Nivel de alerta actualizado: "
                + nivelAnterior + " → " + nuevoNivel
                + " | Config: " + configuracion.getNombrePlantilla());
    }

    @Override
    public void deshacer() {
        configuracion.setNivelAlerta(nivelAnterior);
        System.out.println("[Command - UNDO] Nivel de alerta revertido a: "
                + nivelAnterior + " | Config: " + configuracion.getNombrePlantilla());
    }

    @Override
    public String getNombre() {
        return "CambiarNivelAlerta(" + configuracion.getNombrePlantilla()
                + ", " + nuevoNivel + ")";
    }
}