package com.example.messageBroker.domain;

import java.util.UUID;

import com.example.messageBroker.domain.Constants.ExchangeType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Exchange {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    @Enumerated(EnumType.STRING)
    private ExchangeType type;
}
