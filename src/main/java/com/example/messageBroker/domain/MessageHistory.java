package com.example.messageBroker.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.messageBroker.domain.Constants.MessageHistoryType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageHistory {
    
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private MessageHistoryType state;    

    @ManyToOne
    private Message message;

    private String details;

    private LocalDateTime timestamp;
}
