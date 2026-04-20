package com.smartgrid.service;

import org.springframework.stereotype.Service;

import com.smartgrid.dto.NodoEnergeticoResponse;
import com.smartgrid.patterns.composite.DispositivoEnergetico;
import com.smartgrid.patterns.composite.GrupoEnergetico;
import com.smartgrid.patterns.composite.NodoEnergetico;

@Service
public class ServiceCompositeEnergia {

	
	public NodoEnergeticoResponse construirRedEnergetica() {

        DispositivoEnergetico casa1 = new DispositivoEnergetico("Casa 1", 150);
        DispositivoEnergetico casa2 = new DispositivoEnergetico("Casa 2", 250);
        DispositivoEnergetico industria1 = new DispositivoEnergetico("Industria 1", 500);
        DispositivoEnergetico industria2 = new DispositivoEnergetico("Industria 2", 300);

        GrupoEnergetico zonaNorte = new GrupoEnergetico("Zona Norte");
        zonaNorte.agregarNodo(casa1);
        zonaNorte.agregarNodo(casa2);

        GrupoEnergetico zonaSur = new GrupoEnergetico("Zona Sur");
        zonaSur.agregarNodo(industria1);
        zonaSur.agregarNodo(industria2);

        GrupoEnergetico redPrincipal = new GrupoEnergetico("Red Principal");
        redPrincipal.agregarNodo(zonaNorte);
        redPrincipal.agregarNodo(zonaSur);

        return convertirANodoResponse(redPrincipal);
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
