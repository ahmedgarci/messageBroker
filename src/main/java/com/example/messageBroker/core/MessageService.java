package com.example.messageBroker.core;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.messageBroker.Mappers.Messages.MessageMapper;
import com.example.messageBroker.Repository.MessageHistoryRepository;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.controller.Messages.Responses.MessageResponse;
import com.example.messageBroker.domain.MessageHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    
    private final MessageHistoryRepository messageHistoryRepository;
    private final MessageRepo messageRepo;
    private final MessageMapper messageMapper;


    public List<MessageHistory> getHistoryByMessageId(String messageid){
        
        UUID id = UUID.fromString(messageid);

        return messageHistoryRepository.findByMessageId(id);

    }

    public List<MessageResponse> getMessages() {
    
        List<Object[]> messages = messageRepo.findMessages();

        return messages.stream().map(messageMapper::toMessageResponse).toList();

    }




}
