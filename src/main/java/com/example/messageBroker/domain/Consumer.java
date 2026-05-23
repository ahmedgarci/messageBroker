package com.example.messageBroker.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Consumer {
    @Id
    @GeneratedValue
    private UUID id;
    
    private String name;
}
