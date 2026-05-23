package com.example.messageBroker.core;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.messageBroker.Mappers.Queue.BrodkerQueueCreator;
import com.example.messageBroker.Repository.BrokerQueueRepo;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.controller.Queues.Requests.CreateQueueRequest;
import com.example.messageBroker.domain.BrokerQueue;
import com.example.messageBroker.domain.Message;
import com.example.messageBroker.domain.Constants.MessageStatus;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrokerQueuesService {

    private final BrokerQueueRepo brokerQueueRepo;
    private final BrodkerQueueCreator brokerQueueCreator;
    private final MessageRepo messageRepo;

    @Transactional
    public void create(CreateQueueRequest  request) {
        brokerQueueRepo.findByName(request.name()).ifPresent(q -> {throw new IllegalStateException("Queue already exists: " + request.name());});

        BrokerQueue dlq = null;
        String dlqName = request.name() + "_dlq";

        dlq = brokerQueueRepo.findByName(dlqName)
                .orElseGet(() -> {
                    BrokerQueue newDlq =
                            brokerQueueCreator.createDlq(dlqName);
                    return brokerQueueRepo.save(newDlq);
                });
        BrokerQueue createdQueue = brokerQueueCreator.create(request,dlq);

       brokerQueueRepo.save(createdQueue);
    }

    public List<Message> getDlqMessages(String dlq) {
        BrokerQueue queue = brokerQueueRepo.findByName(dlq).orElseThrow(()-> new EntityNotFoundException("dlq not found"));
        return messageRepo.findByQueueIdAndStatus(queue.getId(),MessageStatus.FAILED);        
    }
    
}
