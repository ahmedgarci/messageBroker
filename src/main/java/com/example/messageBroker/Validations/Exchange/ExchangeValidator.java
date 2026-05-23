package com.example.messageBroker.Validations.Exchange;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.messageBroker.Validations.Exchange.Validations.IExchangeReqValidator;
import com.example.messageBroker.controller.Exchange.Requestss.CreateExchangeReq;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeValidator {

    private final List<IExchangeReqValidator> validators ;

    public void validate(CreateExchangeReq request){

        for(IExchangeReqValidator validator : validators){
            validator.validate(request);
        }

    }
}
