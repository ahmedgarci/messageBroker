package com.example.messageBroker.Mappers.Exchange;


import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.messageBroker.controller.Exchange.Responses.ExchangeResponse;

@Component
public class ExchangeMapper {
    
    public ExchangeResponse toExchangeResponse(Object[] o ){
        return ExchangeResponse.builder()
            .bindingCount((Long) o[4])
            .name((String)o[1])
            .id(String.valueOf(o[0]))
            .type((String) o[2])
            .createdAt((LocalDateTime)o[3])
            .build();
    }
}
