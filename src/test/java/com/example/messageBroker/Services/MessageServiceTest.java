package com.example.messageBroker.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.messageBroker.Mappers.Messages.MessageMapper;
import com.example.messageBroker.Repository.MessageHistoryRepository;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.controller.Messages.Responses.MessageResponse;
import com.example.messageBroker.core.MessageService;
import com.example.messageBroker.domain.MessageHistory;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageHistoryRepository messageHistoryRepository;

    @Mock
    private MessageRepo messageRepo;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private MessageService messageService;

    @Test
    void shouldReturnHistoryByMessageId() {

        UUID id = UUID.randomUUID();

        List<MessageHistory> history = List.of(new MessageHistory());

        when(messageHistoryRepository.findByMessageId(id)).thenReturn(history);

        List<MessageHistory> response = messageService.getHistoryByMessageId(id.toString());

        assertEquals(history, response);

        verify(messageHistoryRepository).findByMessageId(id);
    }
 
    @Test
    void shouldReturnMappedMessages() {
        Object[] row = new Object[]{"msg1", 1L};
        List<Object[]> dbResult = new ArrayList<>();
        dbResult.add(row);

        MessageResponse response = new MessageResponse();

        when(messageRepo.findMessages()).thenReturn(dbResult);

        when(messageMapper.toMessageResponse(row)).thenReturn(response);

        List<MessageResponse> result = messageService.getMessages();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    
        verify(messageRepo).findMessages();
        verify(messageMapper).toMessageResponse(dbResult.get(0));
    }

}
