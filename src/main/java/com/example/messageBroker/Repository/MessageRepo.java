package com.example.messageBroker.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.messageBroker.domain.Message;
import com.example.messageBroker.domain.Constants.MessageStatus;


@Repository
public interface MessageRepo extends JpaRepository<Message,UUID>{

    @Query(value = "SELECT * FROM Message as m where m.status = 'READY' and m.queue_id = :id ORDER BY m.created_at limit 1 FOR UPDATE SKIP LOCKED",nativeQuery = true)
    Message lockNext(@Param("id") UUID id) ;

    @Query(value =  "SELECT * FROM Message as m where m.status = 'PROCESSING' AND  m.processed_at < :cutOff  ",nativeQuery = true)
    List<Message> findStuckProcessingMessages(@Param("cutOff")LocalDateTime cutOff);
    
    List<Message> findByQueueIdAndStatus(UUID queueId, MessageStatus status);
}
