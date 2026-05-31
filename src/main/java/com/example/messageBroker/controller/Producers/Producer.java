package com.example.messageBroker.controller.Producers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messageBroker.controller.Producers.Requests.PublishRequest;
import com.example.messageBroker.core.ProducerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class Producer {
    
    private final ProducerService producerService;

    @PostMapping("/{exchangeName}/messages")
    public void publish(
            @PathVariable String exchangeName,
            @RequestBody PublishRequest request
    ) {
        producerService.publish(exchangeName, request);
    }

    

    @PostMapping("/{messageId}/recover/")
    public void recoverMessageFromDlq(@PathVariable(name = "messageId") String messageId) {
        producerService.recoverMessage(messageId);
    }
    
}
