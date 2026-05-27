package com.example.messageBroker.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.messageBroker.domain.BrokerQueue;

import java.util.List;
import java.util.Optional;


@Repository
public interface BrokerQueueRepo extends JpaRepository<BrokerQueue,UUID>{
    Optional<BrokerQueue> findByName(String name);

    @Query(value = "SELECT q.name, Sum(CASE WHEN m.status='READY' THEN 1 ELSE 0 END),Sum(CASE WHEN m.status='PROCESSING' THEN 1 ELSE 0 END),Sum(CASE WHEN m.status='FAILED' THEN 1 ELSE 0 END) FROM broker_queue as q LEFT JOIN Message as m on  q.id = m.queue_id where m.status != 'Failed' group by q.name ", nativeQuery = true)
    List<Object[]> findAllWithStats();
}
