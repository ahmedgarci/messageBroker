package com.example.messageBroker.controller.Exchange.Requestss;

import com.example.messageBroker.domain.Constants.ExchangeType;

public record CreateExchangeReq(
    String name,
    ExchangeType type
) {
    
}
