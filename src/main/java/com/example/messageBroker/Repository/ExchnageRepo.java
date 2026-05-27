package com.example.messageBroker.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.messageBroker.domain.Exchange;

@Repository
public interface ExchnageRepo extends JpaRepository<Exchange,UUID>{

    Optional<Exchange> findByName(String name);

    @Query(value = "SELECT e.id,e.name,e.type,e.created_at,COUNT(DISTINCT m.id) AS routedMessages FROM exchange e LEFT JOIN binding b ON b.exchange_id = e.id LEFT JOIN message m ON m.queue_id = b.queue_id WHERE m.status <> 'FAILED' GROUP BY e.id,e.name,e.type,e.created_at",nativeQuery = true)
    List<Object[]> findExchangeStats();
}
