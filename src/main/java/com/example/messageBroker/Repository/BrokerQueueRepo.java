package com.example.messageBroker.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.messageBroker.domain.BrokerQueue;
import java.util.Optional;


@Repository
public interface BrokerQueueRepo extends JpaRepository<BrokerQueue,UUID>{
    Optional<BrokerQueue> findByName(String name);
}
