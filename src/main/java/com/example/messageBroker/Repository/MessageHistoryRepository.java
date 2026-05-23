package com.example.messageBroker.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.messageBroker.domain.MessageHistory;

@Repository
public interface MessageHistoryRepository extends JpaRepository<MessageHistory,UUID>{
    

    @Query(value = "SELECT * FROM message_history WHERE message_id = :messageId ",nativeQuery = true)
    public List<MessageHistory> findByMessageId(@Param("messageId")UUID messageId);

}
