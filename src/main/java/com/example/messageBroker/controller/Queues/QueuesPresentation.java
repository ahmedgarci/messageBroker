package com.example.messageBroker.controller.Queues;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messageBroker.controller.Queues.Requests.CreateQueueRequest;
import com.example.messageBroker.controller.Queues.Resp.QueueResponse;
import com.example.messageBroker.core.BrokerQueuesService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/queue")
public class QueuesPresentation {

    private final BrokerQueuesService queueService;

    @PostMapping
    public void create(@RequestBody CreateQueueRequest request) {
        queueService.create(request);
    }

    @GetMapping("/{dlqName}/messages")
    public List<?> getDeadLetterQueue(@PathVariable(name = "dlqName") String dlq) {
        return queueService.getDlqMessages(dlq);
    }

    @GetMapping
    public ResponseEntity<List<QueueResponse>> getQueues() {
        return ResponseEntity.ok().body(queueService.getQueuees());
    }
    
    
    
}
