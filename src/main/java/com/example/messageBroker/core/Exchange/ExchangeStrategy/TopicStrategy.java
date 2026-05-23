package com.example.messageBroker.core.Exchange.ExchangeStrategy;

import org.springframework.stereotype.Component;

import com.example.messageBroker.domain.Constants.ExchangeType;

@Component
public class TopicStrategy implements RoutingStrategy {

    @Override
    public boolean supports(ExchangeType type) {
        return type == ExchangeType.TOPIC;
    }

    @Override
    public boolean matches(String bindingKey, String messageKey) {
        String regex = bindingKey
        .replace(".", "\\.")
        .replace("*", "[^.]+")
        .replace("#", ".*");
        return messageKey.matches(regex);
    }
    
}
