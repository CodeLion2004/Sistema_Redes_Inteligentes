package com.smartgrid.service;

import com.smartgrid.dto.NodoEnergeticoResponse;
import com.smartgrid.model.ConsumoEnergetico;
import com.smartgrid.model.Usuario;
import com.smartgrid.patterns.composite.DispositivoEnergetico;
import com.smartgrid.patterns.composite.GrupoEnergetico;
import com.smartgrid.patterns.composite.NodoEnergetico;
import com.smartgrid.repository.RepositoryConsumoEnergetico;
import com.smartgrid.repository.RepositoryUsuario;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceCompositeEnergia {

    private final RepositoryUsuario usuarioRepository;
    private final RepositoryConsumoEnergetico consumoRepository;

    public ServiceCompositeEnergia(
            RepositoryUsuario usuarioRepository,
            RepositoryConsumoEnergetico consumoRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.consumoRepository = consumoRepository;
    }

    public NodoEnergeticoResponse construirRedEnergetica() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<ConsumoEnergetico> consumos = consumoRepository.findAll();

        Map<String, Usuario> usuariosPorId = new HashMap<>();

        for (Usuario usuario : usuarios) {
            usuariosPorId.put(usuario.getId(), usuario);
        }

        Map<String, Double> consumoPorUsuario = new HashMap<>();

        for (ConsumoEnergetico consumo : consumos) {
            if (consumo.getIdUsuario() == null || consumo.getIdUsuario().isBlank()) {
                continue;
            }

            consumoPorUsuario.merge(
                    consumo.getIdUsuario(),
                    consumo.getConsumo(),
                    Double::sum
            );
        }

        GrupoEnergetico redPrincipal = new GrupoEnergetico("Red Principal");

        Map<String, GrupoEnergetico> zonas = new HashMap<>();

        zonas.put("NORTE", new GrupoEnergetico("Zona Norte"));
        zonas.put("SUR", new GrupoEnergetico("Zona Sur"));
        zonas.put("CENTRO", new GrupoEnergetico("Zona Centro"));
        zonas.put("ORIENTE", new GrupoEnergetico("Zona Oriente"));
        zonas.put("OCCIDENTE", new GrupoEnergetico("Zona Occidente"));
        zonas.put("SIN_ZONA", new GrupoEnergetico("Sin zona asignada"));

        for (Map.Entry<String, Double> entry : consumoPorUsuario.entrySet()) {
            String idUsuario = entry.getKey();
            double consumoTotalUsuario = entry.getValue();

            Usuario usuario = usuariosPorId.get(idUsuario);

            if (usuario == null) {
                continue;
            }

            String zona = normalizarZona(usuario.getZona());

            GrupoEnergetico grupoZona = zonas.getOrDefault(
                    zona,
                    zonas.get("SIN_ZONA")
            );

            DispositivoEnergetico nodoUsuario = new DispositivoEnergetico(
                    usuario.getNombre(),
                    consumoTotalUsuario
            );

            grupoZona.agregarNodo(nodoUsuario);
        }

        for (GrupoEnergetico zona : zonas.values()) {
            redPrincipal.agregarNodo(zona);
        }

        return convertirANodoResponse(redPrincipal);
    }

    private String normalizarZona(String zona) {
        if (zona == null || zona.isBlank()) {
            return "SIN_ZONA";
        }

        String valor = zona.toUpperCase().trim();

        return switch (valor) {
            case "NORTE", "SUR", "CENTRO", "ORIENTE", "OCCIDENTE" -> valor;
            default -> "SIN_ZONA";
        };
    }

    private NodoEnergeticoResponse convertirANodoResponse(NodoEnergetico nodo) {

        if (nodo instanceof DispositivoEnergetico) {
            return new NodoEnergeticoResponse(
                    nodo.getNombre(),
                    "DISPOSITIVO",
                    nodo.obtenerConsumoTotal()
            );
        }

        GrupoEnergetico grupo = (GrupoEnergetico) nodo;

        NodoEnergeticoResponse response = new NodoEnergeticoResponse(
                grupo.getNombre(),
                "GRUPO",
                grupo.obtenerConsumoTotal()
        );

        for (NodoEnergetico hijo : grupo.getNodos()) {
            response.agregarHijo(convertirANodoResponse(hijo));
        }

        return response;
    }

    public double obtenerConsumoTotalRed() {
        return construirRedEnergetica().getConsumoTotal();
    }
}