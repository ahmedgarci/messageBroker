package com.example.messageBroker.Validations.Bindings.Validations;

import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;

public interface IBindingValidator {
    
    public void validate(CreateBindingRequest request);
}
