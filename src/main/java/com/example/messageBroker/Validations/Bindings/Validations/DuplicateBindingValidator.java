package com.example.messageBroker.Validations.Bindings.Validations;

import org.springframework.stereotype.Component;

import com.example.messageBroker.Repository.BindingRepo;
import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DuplicateBindingValidator implements IBindingValidator {
    
    private final BindingRepo bindingRepo;


    @Override
    public void validate(CreateBindingRequest request) {
        
        boolean exists = bindingRepo.existsByExchangeNameAndQueueNameAndRoutingKey(
            request.exchangeName(),
            request.queueName(),
            request.routingKey()
        );

        if(exists){
            throw new IllegalArgumentException("binding already exists");
        }
    }
    
}
