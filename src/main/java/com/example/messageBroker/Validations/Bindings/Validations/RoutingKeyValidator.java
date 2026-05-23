package com.example.messageBroker.Validations.Bindings.Validations;

import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;

public class RoutingKeyValidator implements IBindingValidator {

    @Override
    public void validate(CreateBindingRequest request) {
        if(request.routingKey() == null || request.routingKey().isBlank()){
            throw new IllegalArgumentException("routing key should not be empty");
        }
    }
    
}
