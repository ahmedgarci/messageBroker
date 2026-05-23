package com.example.messageBroker.Validations.Bindings.Validations;

import org.springframework.stereotype.Component;

import com.example.messageBroker.Repository.BrokerQueueRepo;
import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BindingQueueValidator implements IBindingValidator{
    
    private final BrokerQueueRepo brokerQueueRepo;

    @Override
     public void validate(CreateBindingRequest req){
        if(req.queueName() == null || req.queueName().isBlank()){
            throw new IllegalArgumentException("queue name should not be empty");
        }
        if(!brokerQueueRepo.findByName(req.queueName()).isPresent()){
            throw new IllegalArgumentException("exchange already exists");
        }
    
    }  
    
}
