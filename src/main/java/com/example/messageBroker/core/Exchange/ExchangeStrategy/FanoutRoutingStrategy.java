package com.example.messageBroker.core.Exchange.ExchangeStrategy;

import org.springframework.stereotype.Component;

import com.example.messageBroker.domain.Constants.ExchangeType;

@Component
public class FanoutRoutingStrategy implements RoutingStrategy {

    @Override
    public boolean supports(ExchangeType type) {
        return type == ExchangeType.FANOUT;
    }

    @Override
    public boolean matches(String bindingKey, String messageKey) {
        return true;
    }
    
}
