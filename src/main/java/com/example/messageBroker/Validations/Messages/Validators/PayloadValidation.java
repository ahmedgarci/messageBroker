package com.example.messageBroker.Validations.Messages.Validators;

import org.springframework.stereotype.Component;

import com.example.messageBroker.Validations.Messages.Validators.Interfaces.IMessageValidator;
import com.example.messageBroker.controller.Producers.Requests.PublishRequest;

@Component
public class PayloadValidation implements IMessageValidator{

    @Override
    public void validate(PublishRequest request) {
        if(request.payload() == null || request.payload().isBlank()){
            throw new IllegalArgumentException("payload is not valid");
        }
        if(request.payload().length() > 1_000_000){
            throw new IllegalArgumentException("payload size is not valid");
        }
    }
    
}
