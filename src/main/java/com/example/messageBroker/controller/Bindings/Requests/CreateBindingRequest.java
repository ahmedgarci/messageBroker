package com.example.messageBroker.controller.Bindings.Requests;

public record CreateBindingRequest(
    String name,
    String exchangeName,
    String queueName,
    String routingKey
) {
    
}
