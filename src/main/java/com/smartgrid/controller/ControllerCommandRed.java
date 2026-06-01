package com.smartgrid.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.smartgrid.model.ConfiguracionRed;
import com.smartgrid.patterns.command.*;
import com.smartgrid.patterns.prototype.RegistroConfiguraciones;

@RestController
@RequestMapping("/api/configuracion-red")
@CrossOrigin("*")
public class ControllerCommandRed {

    private final InvocadorComandosRed invocador;

    public ControllerCommandRed(InvocadorComandosRed invocador) {
        this.invocador = invocador;
    }

    @GetMapping("/historial")
    public List<String> historial() {
        return invocador.obtenerHistorial();
    }

    @PostMapping("/deshacer")
    public String deshacer() {
        invocador.deshacerUltimo();
        return "Último comando deshecho correctamente";
    }

    @PostMapping("/ejecutar")
    public String ejecutar(@RequestBody ComandoRedRequest request) {

        ConfiguracionRed config =
                RegistroConfiguraciones.obtenerClon(request.getTipoConfig());

        if(config == null) {
            return "No existe la configuración: " + request.getTipoConfig();
        }

        ComandoRed comando = null;

        switch(request.getTipoComando()) {

            case "ACTUALIZAR_LIMITE":
                comando = new ComandoActualizarLimiteConsumo(
                        config,
                        request.getNuevoLimite()
                );
                break;

            case "CAMBIAR_FUENTE":
                comando = new ComandoCambiarFuenteEnergia(
                        config,
                        request.getNuevaFuente()
                );
                break;

            case "CAMBIAR_NIVEL_ALERTA":
                comando = new ComandoCambiarNivelAlerta(
                        config,
                        request.getNuevoNivel()
                );
                break;
        }

        if(comando == null) {
            return "Tipo de comando inválido";
        }

        invocador.ejecutar(comando);

        return "Comando ejecutado: " + comando.getNombre();
    }
}