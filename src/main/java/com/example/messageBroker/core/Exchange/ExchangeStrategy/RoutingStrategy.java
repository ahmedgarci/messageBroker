package com.example.messageBroker.core.Exchange.ExchangeStrategy;

import com.example.messageBroker.domain.Constants.ExchangeType;

public interface RoutingStrategy {

    boolean supports(ExchangeType type);

    boolean matches(String bindingKey,String messageKey);
    
}
