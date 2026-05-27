package com.example.messageBroker.controller.Messages.Responses;

import java.time.LocalDateTime;
import java.util.UUID;

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
public class MessageResponse {
    private UUID message_id;
    private String 	queue;
    private String	status;
    private String type;
    private Integer retries;
    private LocalDateTime created_at;
}
