package com.example.messageBroker.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Binding {
    
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne()
    private Exchange exchange;  

    @ManyToOne
    private BrokerQueue queue;
    
    private String routingKey;
}
