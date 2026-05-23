package com.example.messageBroker.Validations.Bindings;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.messageBroker.Validations.Bindings.Validations.IBindingValidator;
import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BindingValidator {
    
    private final List<IBindingValidator> validators;

    public void validate(CreateBindingRequest request){
        for(IBindingValidator validator : validators){
            validator.validate(request);
        }
    }
}
