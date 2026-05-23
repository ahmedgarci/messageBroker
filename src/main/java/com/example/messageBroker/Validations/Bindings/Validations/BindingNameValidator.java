package com.example.messageBroker.Validations.Bindings.Validations;

import org.springframework.stereotype.Component;

import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;

@Component
public class BindingNameValidator implements IBindingValidator{

    @Override
    public void validate(CreateBindingRequest request) {
        if(request.name() == null || request.name().isBlank()){
            throw new IllegalArgumentException("name should not be empty");
        }
    }
    
}
