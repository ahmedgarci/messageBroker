package com.example.messageBroker.controller.Messages.Responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DlqMessageResponse {
    
    private UUID message_id;
    private String 	queue;
    private Integer retries;
}
