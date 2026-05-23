package com.example.messageBroker.core;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.messageBroker.Repository.BindingRepo;
import com.example.messageBroker.Repository.ExchnageRepo;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.Validations.Messages.ValidationPipeline;
import com.example.messageBroker.controller.Producers.Requests.PublishRequest;
import com.example.messageBroker.domain.Binding;
import com.example.messageBroker.domain.BrokerQueue;
import com.example.messageBroker.domain.Exchange;
import com.example.messageBroker.domain.Message;
import com.example.messageBroker.domain.Constants.MessageStatus;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final BindingRepo bindingRepo;
    private final ExchnageRepo exchangeRepo;
    private final ValidationPipeline validationPipeline;
    private final RoutingService routingService;
    private final MessageRepo messageRepo;

    @Transactional
    public void publish(String exchangeName, PublishRequest publishRequest) {

        validationPipeline.validate(publishRequest);

        Exchange exchange = exchangeRepo.findByName(exchangeName).orElseThrow(()-> new EntityNotFoundException("exchange not found"));

        List<Binding> bindings = bindingRepo.findByExchangeAndRoutingKey(exchange.getId());
                
        routingService.route(publishRequest, bindings,exchange);

    }

    public void recoverMessage(String messageId) {

        Message message =  messageRepo.findById(UUID.fromString(messageId)).orElseThrow(()-> new EntityNotFoundException("message not found"));

        BrokerQueue originalQueue = message.getOriginialQueue();

        message.setQueue(originalQueue);

        message.setStatus(MessageStatus.READY);

        message.setProcessed_at(null);

        message.setRetryCount(0);
    }
    
}
