package com.example.messageBroker.Mappers.Messages;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.messageBroker.domain.Message;
import com.example.messageBroker.domain.MessageHistory;
import com.example.messageBroker.domain.Constants.MessageHistoryType;

@Component
public class MessageHistoryFactory {
    
    public MessageHistory create(Message message, MessageHistoryType type,String details){
        return MessageHistory.builder()
            .message(message)
            .details(details)
            .state(type)
            .timestamp(LocalDateTime.now())
        .build();
    }
}
