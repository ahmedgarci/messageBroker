package com.example.messageBroker.Validations.Exchange.Validations;

import org.springframework.stereotype.Component;

import com.example.messageBroker.Repository.ExchnageRepo;
import com.example.messageBroker.controller.Exchange.Requestss.CreateExchangeReq;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NameValidator implements IExchangeReqValidator{
    
    private final ExchnageRepo exchnageRepo;

    public void validate(CreateExchangeReq req){
        if(req.name() == null || req.name().isBlank()){
            throw new IllegalArgumentException("name should not be empty");
        }
        if(exchnageRepo.findByName(req.name()).isPresent()){
            throw new IllegalArgumentException("exchange already exists");
        }
    
    }    
}
