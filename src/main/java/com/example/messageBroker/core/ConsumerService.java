package com.example.messageBroker.core;


import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.messageBroker.Mappers.Messages.MessageHistoryFactory;
import com.example.messageBroker.Repository.BrokerQueueRepo;
import com.example.messageBroker.Repository.MessageHistoryRepository;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.controller.Consumer.Requests.AckRequest;
import com.example.messageBroker.domain.BrokerQueue;
import com.example.messageBroker.domain.Message;
import com.example.messageBroker.domain.MessageHistory;
import com.example.messageBroker.domain.Constants.MessageHistoryType;
import com.example.messageBroker.domain.Constants.MessageStatus;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final MessageRepo messageRepo;
    private final BrokerQueueRepo brokerQueueRepo;
    private final MessageHistoryRepository messageHistoryRepository;
    private final MessageHistoryFactory messageHistoryFactory;

    @Transactional
    public Message consume(String queueName) {

        BrokerQueue queue = brokerQueueRepo.findByName(queueName).orElseThrow(()-> new EntityNotFoundException("queueName was not found"));

        Message message = messageRepo.lockNext(queue.getId());

        if(message == null) throw new IllegalStateException("no message is available");

        message.setStatus(MessageStatus.PROCESSING);

        message.setProcessed_at(LocalDateTime.now());

        logHistory(message,MessageHistoryType.onConsume,"status changed to PROCESSING");

        return message;

    }   

    @Transactional
    public void acknowledgeMessage(AckRequest request){

        Message message =  messageRepo.lockById(UUID.fromString(request.messageId())).orElseThrow(()-> new EntityNotFoundException("message was not found"));
        
        if(message.getStatus() == MessageStatus.ACKED) return;

        if(message.getStatus() != MessageStatus.PROCESSING){

            throw new IllegalStateException("Invalid ACK state");

        }
                
        logHistory(message, MessageHistoryType.onACK, "message was acked by worker ");

        message.setStatus(MessageStatus.ACKED);

        message.setProcessed_at(LocalDateTime.now());
    }

    private void logHistory(Message message,MessageHistoryType type,String details) {
        
        MessageHistory history = messageHistoryFactory.create(message, type, details);

        messageHistoryRepository.save(history);

    }
    
}
