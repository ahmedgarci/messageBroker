package com.example.messageBroker.Mappers.Queue;

import org.springframework.stereotype.Component;

import com.example.messageBroker.controller.Queues.Requests.CreateQueueRequest;
import com.example.messageBroker.domain.BrokerQueue;

@Component
public class BrodkerQueueCreator {
    
    public BrokerQueue create(CreateQueueRequest request,BrokerQueue dlq){
        return BrokerQueue.builder()
        .deadLetterQueue(dlq)
        .name(request.name()).build();
    }


    public BrokerQueue createDlq(String dlqName) {
        return BrokerQueue.builder().name(dlqName).build();
    }
}
