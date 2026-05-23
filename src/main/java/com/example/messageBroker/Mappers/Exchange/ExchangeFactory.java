package com.example.messageBroker.Mappers.Exchange;

import org.springframework.stereotype.Component;

import com.example.messageBroker.controller.Exchange.Requestss.CreateExchangeReq;
import com.example.messageBroker.domain.Exchange;

@Component
public class ExchangeFactory {
    
    public Exchange create(CreateExchangeReq req){
        return Exchange.builder().name(req.name()).type(req.type()).build();
    }
}
