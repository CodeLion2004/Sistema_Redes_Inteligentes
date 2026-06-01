package com.smartgrid.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlertaWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public AlertaWebSocketService(
            SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;
    }

    public void enviarAlerta(String mensaje){

        messagingTemplate.convertAndSend(
                "/topic/alertas",
                mensaje);

    }
}