package com.example.messageBroker.core.MessageEventListener;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.messageBroker.domain.Message;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageEventApplicationPublisher {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    @TransactionalEventListener(phase =  TransactionPhase.AFTER_COMMIT)
    public void onMessageCreated(MessageEvent messageEvent){

        Message message = messageEvent.message();

        messagingTemplate.convertAndSend("/broker/queues", message);

    }
}
