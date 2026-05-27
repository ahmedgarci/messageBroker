package com.example.messageBroker.controller.Consumer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messageBroker.controller.Consumer.Requests.AckRequest;
import com.example.messageBroker.core.ConsumerService;
import com.example.messageBroker.core.MessageService;
import com.example.messageBroker.domain.Message;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping
@RequiredArgsConstructor
public class ConsumerPresentation {
    
    private final ConsumerService consumerService;
    private final MessageService messageService;

    @GetMapping("/queues/{queueName}/messages/next")
    public Message processMessage(@PathVariable String queueName) {
        return consumerService.consume(queueName);
    }

    @MessageMapping("/ack")
    public void ack(@RequestBody AckRequest request) {
        consumerService.acknowledgeMessage(request);
    } 
    
    
    @GetMapping("/history/{messageId}")
    public List<?> getMessageHistory(@PathVariable(name = "messageId") String messageId) {
        return messageService.getHistoryByMessageId(messageId);
    } 
    

  


    
}
