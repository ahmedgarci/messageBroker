package com.example.messageBroker.Mappers.Messages;


import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.example.messageBroker.controller.Producers.Requests.PublishRequest;
import com.example.messageBroker.domain.BrokerQueue;
import com.example.messageBroker.domain.Message;

@Component
public class MessageFactory {
    
    public Message create(PublishRequest publishRequest,BrokerQueue queue){

        return Message.builder()
        .queue(queue)
        .type(publishRequest.messageType())
        .originialQueue(queue)
        .payload(publishRequest.payload())
        .headers(publishRequest.headers() != null ? publishRequest.headers() : new HashMap<>())
        .build();
    }

}
