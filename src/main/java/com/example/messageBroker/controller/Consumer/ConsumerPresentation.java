package com.example.messageBroker.controller.Consumer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messageBroker.core.ConsumerService;
import com.example.messageBroker.core.MessageHistoryService;
import com.example.messageBroker.domain.Message;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping
@RequiredArgsConstructor
public class ConsumerPresentation {
    
    private final ConsumerService consumerService;
    private final MessageHistoryService messageHistoryService;

    @GetMapping("/queues/{queueName}/messages/next")
    public Message processMessage(@PathVariable String queueName) {
        return consumerService.consume(queueName);
    }

    @PostMapping("/messages/{messageId}/ack")
    public void ack(@PathVariable String messageId) {
        consumerService.acknowledgeMessage(messageId);
    } 
    
    
    @GetMapping("/history/{messageId}")
    public List<?> getMessageHistory(@PathVariable(name = "messageId") String messageId) {
        return messageHistoryService.getHistoryByMessageId(messageId);
    } 
    

  


    
}
