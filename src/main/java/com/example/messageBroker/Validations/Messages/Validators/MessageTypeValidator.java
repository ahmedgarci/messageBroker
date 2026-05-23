package com.example.messageBroker.Validations.Messages.Validators;

import org.springframework.stereotype.Component;

import com.example.messageBroker.Validations.Messages.Validators.Interfaces.IMessageValidator;
import com.example.messageBroker.controller.Producers.Requests.PublishRequest;

@Component
public class MessageTypeValidator implements IMessageValidator {

    @Override
    public void validate(PublishRequest request) {

        if (request.messageType() == null) {
            throw new IllegalArgumentException("Message type is required");
        }
    } 
    
}
