package com.smartgrid.patterns.command;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Component
public class InvocadorComandosRed {

    private final Deque<ComandoRed> historial = new ArrayDeque<>();

    
    public void ejecutar(ComandoRed comando) {
        comando.ejecutar();
        historial.push(comando);
    }

  
    public void deshacerUltimo() {
        if (historial.isEmpty()) {
            System.out.println("[Invoker] No hay comandos para deshacer.");
            return;
        }
        ComandoRed ultimo = historial.pop();
        ultimo.deshacer();
    }


    public List<String> obtenerHistorial() {
        List<String> nombres = new ArrayList<>();
        for (ComandoRed cmd : historial) {
            nombres.add(cmd.getNombre());
        }
        return nombres;
    }

    public void limpiarHistorial() {
        historial.clear();
        System.out.println("[Invoker] Historial limpiado.");
    }
}