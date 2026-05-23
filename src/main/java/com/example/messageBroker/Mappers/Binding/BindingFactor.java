package com.example.messageBroker.Mappers.Binding;

import org.springframework.stereotype.Component;

import com.example.messageBroker.Repository.BrokerQueueRepo;
import com.example.messageBroker.Repository.ExchnageRepo;
import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;
import com.example.messageBroker.domain.Binding;
import com.example.messageBroker.domain.BrokerQueue;
import com.example.messageBroker.domain.Exchange;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BindingFactor {
    
    private final ExchnageRepo exchangeRepo;
    private final BrokerQueueRepo queueRepo;

    public Binding create(CreateBindingRequest request){
        Exchange exchange = exchangeRepo.findByName(request.exchangeName()).orElseThrow(()-> new EntityNotFoundException("exchange not found"));
        BrokerQueue targetQueue = queueRepo.findByName(request.queueName()).orElseThrow(()-> new EntityNotFoundException("queue not found"));
        return Binding.builder().exchange(exchange).routingKey(request.routingKey()).queue(targetQueue).build();
    }
}
