package com.example.messageBroker.controller.Exchange.Responses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeResponse {
    
    private String id;
    private String name;
    private String type;
    private Long bindingCount;
    private LocalDateTime createdAt;
}
