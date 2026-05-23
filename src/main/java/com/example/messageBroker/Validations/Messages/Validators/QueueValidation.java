package com.example.messageBroker.Validations.Messages.Validators;

import org.springframework.stereotype.Component;

import com.example.messageBroker.Validations.Messages.Validators.Interfaces.IMessageValidator;
import com.example.messageBroker.controller.Producers.Requests.PublishRequest;


@Component
public class QueueValidation implements IMessageValidator{

    @Override
    public void validate(PublishRequest request) {
        if (request.routingKey() == null || request.routingKey().isBlank()) {
            throw new IllegalArgumentException("Queue does not exist");
        }
    }
    
}
