package com.example.messageBroker.controller.Producers.Requests;

import java.util.Map;

import com.example.messageBroker.domain.Constants.MessageType;


public record PublishRequest(
    String payload,
    MessageType messageType,
    Map<String,String> headers,
    String routingKey,
    Integer delay
) {
    
}
