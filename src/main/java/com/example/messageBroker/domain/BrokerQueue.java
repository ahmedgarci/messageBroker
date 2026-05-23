package com.example.messageBroker.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class BrokerQueue {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    
    @OneToOne
    private BrokerQueue deadLetterQueue;

}
