package com.example.messageBroker.core;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.messageBroker.Repository.MessageHistoryRepository;
import com.example.messageBroker.domain.MessageHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageHistoryService {
    
    private final MessageHistoryRepository messageHistoryRepository;

    public List<MessageHistory> getHistoryByMessageId(String messageid){
        
        UUID id = UUID.fromString(messageid);

        return messageHistoryRepository.findByMessageId(id);

    }

}
