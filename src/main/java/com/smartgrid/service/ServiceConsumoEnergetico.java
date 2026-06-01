package com.smartgrid.service;

import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.repository.RepositoryConsumoEnergetico;
import org.springframework.stereotype.Service;

import java.util.List;
import com.smartgrid.patterns.memento.HistorialConsumoEnergetico;
@Service
public class ServiceConsumoEnergetico {

    private final RepositoryConsumoEnergetico repository;
    private final HistorialConsumoEnergetico historialConsumoEnergetico;

    public ServiceConsumoEnergetico(RepositoryConsumoEnergetico repository,
	            HistorialConsumoEnergetico historialConsumoEnergetico) {
	this.repository = repository;
	this.historialConsumoEnergetico = historialConsumoEnergetico;
	}

    public ConsumoEnergetico save(ConsumoEnergetico consumo) {
        return repository.save(consumo);
    }

    public List<ConsumoEnergetico> findAll() {
        return repository.findAll();
    }

    public List<ConsumoEnergetico> findAllExternos() {
        return repository.findAll()
                .stream()
                .filter(this::esConsumoExterno)
                .toList();
    }

    public ConsumoEnergetico saveExterno(ConsumoEnergetico consumo) {
        consumo.setIdUsuario(null);
        return repository.save(consumo);
    }

    public ConsumoEnergetico update(String id, ConsumoEnergetico consumoActualizado) {
        ConsumoEnergetico consumoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumo no encontrado con id: " + id));

        consumoExistente.setIdDispositivo(consumoActualizado.getIdDispositivo());
        consumoExistente.setIdUsuario(consumoActualizado.getIdUsuario());
        consumoExistente.setConsumo(consumoActualizado.getConsumo());
        consumoExistente.setFuenteEnergia(consumoActualizado.getFuenteEnergia());
        consumoExistente.setMarcaTiempo(consumoActualizado.getMarcaTiempo());

        return repository.save(consumoExistente);
    }

    public ConsumoEnergetico updateExterno(String id, ConsumoEnergetico consumoActualizado) {
        ConsumoEnergetico consumoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumo externo no encontrado con id: " + id));

        if (!esConsumoExterno(consumoExistente)) {
            throw new IllegalStateException(
                    "No se puede modificar este consumo porque pertenece al flujo de usuarios/facturación."
            );
        }

        historialConsumoEnergetico.guardar(id, consumoExistente.guardarEstado());

        consumoExistente.setIdDispositivo(consumoActualizado.getIdDispositivo());
        consumoExistente.setIdUsuario(null);
        consumoExistente.setConsumo(consumoActualizado.getConsumo());
        consumoExistente.setFuenteEnergia(consumoActualizado.getFuenteEnergia());
        consumoExistente.setMarcaTiempo(consumoActualizado.getMarcaTiempo());

        return repository.save(consumoExistente);
    }

    public void deleteById(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Consumo no encontrado con id: " + id);
        }

        repository.deleteById(id);
    }

    public void deleteExternoById(String id) {
        ConsumoEnergetico consumo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumo externo no encontrado con id: " + id));

        if (!esConsumoExterno(consumo)) {
            throw new IllegalStateException(
                    "No se puede eliminar este consumo desde Registro de Consumo porque pertenece al flujo de usuarios/facturación."
            );
        }

        repository.deleteById(id);
    }

    public long contarConsumos() {
        return repository.count();
    }

    private boolean esConsumoExterno(ConsumoEnergetico consumo) {
        return consumo.getIdUsuario() == null || consumo.getIdUsuario().isBlank();
    }
    
    public ConsumoEnergetico restaurarUltimoCambioExterno(String id) {
        ConsumoEnergetico consumoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumo externo no encontrado con id: " + id));

        if (!esConsumoExterno(consumoExistente)) {
            throw new IllegalStateException(
                    "No se puede restaurar este consumo porque pertenece al flujo de usuarios/facturación."
            );
        }

        var estadoAnterior = historialConsumoEnergetico.restaurarUltimo(id);

        consumoExistente.restaurarEstado(estadoAnterior);
        consumoExistente.setIdUsuario(null);

        return repository.save(consumoExistente);
    }

    public boolean tieneHistorialExterno(String id) {
        return historialConsumoEnergetico.tieneHistorial(id);
    }
}