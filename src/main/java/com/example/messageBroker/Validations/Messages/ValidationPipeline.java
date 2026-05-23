package com.example.messageBroker.Validations.Messages;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.messageBroker.Validations.Messages.Validators.Interfaces.IMessageValidator;
import com.example.messageBroker.controller.Producers.Requests.PublishRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationPipeline {
    
    private final List<IMessageValidator> validators;
    
    public void validate(PublishRequest request){

        for(IMessageValidator validator : validators){
            validator.validate(request);
        }
    }
}
