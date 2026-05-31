package com.example.messageBroker.core;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.messageBroker.Mappers.Messages.MessageFactory;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.controller.Producers.Requests.PublishRequest;
import com.example.messageBroker.core.Exchange.ExchangeStrategy.RoutingStrategy;
import com.example.messageBroker.core.Exchange.ExchangeStrategy.RoutingStrategyResolver;
import com.example.messageBroker.core.MessageEventListener.MessageEvent;
import com.example.messageBroker.domain.Binding;
import com.example.messageBroker.domain.Exchange;
import com.example.messageBroker.domain.Message;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoutingService {
    
    private final MessageFactory messageFactory;
    private final MessageRepo messageRepo;
    private final RoutingStrategyResolver strategyResolver;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void route(PublishRequest request, List<Binding> bindings,Exchange exchange) {
        RoutingStrategy strategy = strategyResolver.resolve(exchange.getType());

        for(Binding binding : bindings){
            if(strategy.matches(binding.getRoutingKey(),request.routingKey())){

                Message message = messageFactory.create(request, binding.getQueue());

                messageRepo.save(message);

                applicationEventPublisher.publishEvent(new MessageEvent(message));

            }
        }
    }







}