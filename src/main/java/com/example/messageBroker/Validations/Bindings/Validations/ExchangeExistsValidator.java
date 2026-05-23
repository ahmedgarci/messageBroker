package com.example.messageBroker.Validations.Bindings.Validations;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.messageBroker.Repository.ExchnageRepo;
import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Order(1)
public class ExchangeExistsValidator implements IBindingValidator{
    
    private final ExchnageRepo exchnageRepo;

    @Override
    public void validate(CreateBindingRequest request) {

         if(request.exchangeName() == null || request.exchangeName().isBlank()){
            throw new IllegalArgumentException("exchange name should not be empty");
        }

        if(!exchnageRepo.findByName(request.exchangeName()).isPresent()){
            throw new IllegalArgumentException("exchange is not  present");
        }
    }
    
}
