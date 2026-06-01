package com.smartgrid.patterns.memento;

import java.time.LocalDateTime;

public class ConsumoEnergeticoMemento {

    private final String idDispositivo;
    private final String idUsuario;
    private final double consumo;
    private final String fuenteEnergia;
    private final LocalDateTime marcaTiempo;

    public ConsumoEnergeticoMemento(String idDispositivo,
                                    String idUsuario,
                                    double consumo,
                                    String fuenteEnergia,
                                    LocalDateTime marcaTiempo) {
        this.idDispositivo = idDispositivo;
        this.idUsuario = idUsuario;
        this.consumo = consumo;
        this.fuenteEnergia = fuenteEnergia;
        this.marcaTiempo = marcaTiempo;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public double getConsumo() {
        return consumo;
    }

    public String getFuenteEnergia() {
        return fuenteEnergia;
    }

    public LocalDateTime getMarcaTiempo() {
        return marcaTiempo;
    }
}