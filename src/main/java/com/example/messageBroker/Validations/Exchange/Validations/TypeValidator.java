package com.example.messageBroker.Validations.Exchange.Validations;

import org.springframework.stereotype.Component;

import com.example.messageBroker.controller.Exchange.Requestss.CreateExchangeReq;

@Component
public class TypeValidator implements IExchangeReqValidator {

    @Override
    public void validate(CreateExchangeReq req) {
        if(req.type() == null){
            throw new IllegalArgumentException("type is null");
        }
    }
    
}
