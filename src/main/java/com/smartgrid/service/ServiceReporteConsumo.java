package com.smartgrid.service;

import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.repository.RepositoryConsumoEnergetico;
import com.smartgrid.dto.TendenciaConsumoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ServiceReporteConsumo {

    @Autowired
    private RepositoryConsumoEnergetico consumoRepository;

    public TendenciaConsumoDTO obtenerTendencias(String periodo) {

        List<ConsumoEnergetico> consumos = consumoRepository.findAll();

        Map<String, Double> mapaDiario = new LinkedHashMap<>();

        for (ConsumoEnergetico c : consumos) {
            if (c.getMarcaTiempo() != null) {
                String fechaStr = c.getMarcaTiempo().toLocalDate().toString();
                mapaDiario.merge(fechaStr, c.getConsumo(), Double::sum);
            }
        }

        List<String> fechas = new ArrayList<>(mapaDiario.keySet());
        List<Double> valores = new ArrayList<>(mapaDiario.values());

        double consumoTotal = valores.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return new TendenciaConsumoDTO(fechas, valores, consumoTotal);
    }
}