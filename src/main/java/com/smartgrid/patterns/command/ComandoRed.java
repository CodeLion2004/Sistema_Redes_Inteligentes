package com.smartgrid.patterns.command;

public interface ComandoRed {

    void ejecutar();

    void deshacer();

    String getNombre();
}