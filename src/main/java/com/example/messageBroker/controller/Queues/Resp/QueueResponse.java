package com.example.messageBroker.controller.Queues.Resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueResponse {
    private String name;
    private long ready;
    private long processing;
    private long dlq;
    private long totalMessages;    

}
