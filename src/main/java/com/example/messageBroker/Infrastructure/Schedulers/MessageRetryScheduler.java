package com.example.messageBroker.Infrastructure.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.messageBroker.Mappers.Messages.MessageHistoryFactory;
import com.example.messageBroker.Repository.MessageHistoryRepository;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.domain.BrokerQueue;
import com.example.messageBroker.domain.Message;
import com.example.messageBroker.domain.MessageHistory;
import com.example.messageBroker.domain.Constants.MessageHistoryType;
import com.example.messageBroker.domain.Constants.MessageStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageRetryScheduler {
    
    private final MessageRepo messageRepo;
    private static final Integer TIMEOUT_SECONDS = 30;
    private static final Integer MAX_RETRY=5;
    private final MessageHistoryFactory messageHistoryFactory;
    private final MessageHistoryRepository messageHistoryRepository;


    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void recoverStuckMessages(){

        LocalDateTime cutOff = LocalDateTime.now().minusSeconds(TIMEOUT_SECONDS);

        List<Message> messages = messageRepo.findStuckProcessingMessages(cutOff);

        for(Message message : messages){

            if(message.getRetryCount() != null && message.getRetryCount() > MAX_RETRY){

                moveToDeadLetterQueue(message);         

            }else{

                retryMessage(message);

            }
        
        }

    }

    private void retryMessage(Message message){

        message.setStatus(MessageStatus.READY);

        message.setProcessed_at(null);

        message.setRetryCount(message.getRetryCount() == null ? 1 : message.getRetryCount()+1);

        logHistory(message, MessageHistoryType.onRetry, String.format("message is retrying  for the %s time : ",message.getRetryCount()+1));

    }

    private void moveToDeadLetterQueue(Message message){

        BrokerQueue dlq = message.getQueue().getDeadLetterQueue();

        if(dlq == null){

            message.setStatus(MessageStatus.FAILED);

            return;
        }

        message.setQueue(dlq);

        message.setStatus(MessageStatus.FAILED);

        message.setProcessed_at(null);

        logHistory(message,MessageHistoryType.onDLQ, "message is being transferred to dead letter queue");

    }

    private void logHistory(Message message,MessageHistoryType type,String details) {
        
        MessageHistory history = messageHistoryFactory.create(message, type, details);

        messageHistoryRepository.save(history);

    }

}
