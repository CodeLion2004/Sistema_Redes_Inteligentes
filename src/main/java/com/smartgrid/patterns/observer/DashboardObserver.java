package com.smartgrid.patterns.observer;

import com.smartgrid.service.AlertaWebSocketService;

public class DashboardObserver
        implements ObservadorEnergia {

    private AlertaWebSocketService webSocketService;

    public DashboardObserver(
            AlertaWebSocketService webSocketService){

        this.webSocketService = webSocketService;

    }

    @Override
    public void actualizar(String mensaje) {

        System.out.println(
                "DASHBOARD: " + mensaje);

        webSocketService.enviarAlerta(mensaje);

    }

}