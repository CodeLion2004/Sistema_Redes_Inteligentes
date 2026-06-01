package com.smartgrid.patterns.memento;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

@Component
public class HistorialConsumoEnergetico {

    private final Map<String, Deque<ConsumoEnergeticoMemento>> historialPorConsumo = new HashMap<>();

    public void guardar(String idConsumo, ConsumoEnergeticoMemento estado) {
        historialPorConsumo
                .computeIfAbsent(idConsumo, id -> new ArrayDeque<>())
                .push(estado);
    }

    public ConsumoEnergeticoMemento restaurarUltimo(String idConsumo) {
        Deque<ConsumoEnergeticoMemento> historial = historialPorConsumo.get(idConsumo);

        if (historial == null || historial.isEmpty()) {
            throw new IllegalStateException("No hay estados previos para restaurar este consumo.");
        }

        return historial.pop();
    }

    public boolean tieneHistorial(String idConsumo) {
        Deque<ConsumoEnergeticoMemento> historial = historialPorConsumo.get(idConsumo);
        return historial != null && !historial.isEmpty();
    }
}