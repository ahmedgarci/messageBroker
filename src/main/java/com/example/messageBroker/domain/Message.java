package com.example.messageBroker.domain;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.example.messageBroker.domain.Constants.MessageStatus;
import com.example.messageBroker.domain.Constants.MessageType;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Message {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String payload;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private LocalDateTime created_at; 

    private LocalDateTime processed_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="queue_id")
    private BrokerQueue queue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="original_queue_id")
    private BrokerQueue originialQueue;
    
    private LocalDateTime available_at;

    private Integer retryCount = 0;

    @ElementCollection
    @CollectionTable(name = "message_headers")
    private Map<String,String> headers;

    @PrePersist
    public void onCreate() {
        this.created_at = LocalDateTime.now();
        if (this.status == null) {
            this.status = MessageStatus.READY;
        }
    }}
