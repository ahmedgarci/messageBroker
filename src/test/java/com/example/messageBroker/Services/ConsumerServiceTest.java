package com.example.messageBroker.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.messageBroker.Mappers.Messages.MessageHistoryFactory;
import com.example.messageBroker.Repository.BrokerQueueRepo;
import com.example.messageBroker.Repository.MessageHistoryRepository;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.controller.Consumer.Requests.AckRequest;
import com.example.messageBroker.core.ConsumerService;
import com.example.messageBroker.domain.BrokerQueue;
import com.example.messageBroker.domain.Message;
import com.example.messageBroker.domain.MessageHistory;
import com.example.messageBroker.domain.Constants.MessageHistoryType;
import com.example.messageBroker.domain.Constants.MessageStatus;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceTest {

    @Mock
    private MessageRepo messageRepo;
    @Mock
    private BrokerQueueRepo brokerQueueRepo;
    @Mock
    private MessageHistoryRepository messageHistoryRepository;
    @Mock
    private MessageHistoryFactory messageHistoryFactory;

    @InjectMocks
    private ConsumerService consumerService;

    @Test
    public void shouldConsumerMessageSuccessfully(){

        BrokerQueue queue = BrokerQueue.builder().id(UUID.randomUUID()).name("orders").build();

        Message message = new Message();

        MessageHistory messageHistory = new MessageHistory();

        when(brokerQueueRepo.findByName(queue.getName())).thenReturn(Optional.of(queue));

        when(messageRepo.lockNext(queue.getId())).thenReturn(message);
        
        when(messageHistoryFactory.create(message, MessageHistoryType.onConsume, "status changed to PROCESSING")).thenReturn(messageHistory);

        Message result = consumerService.consume("orders");

        assertEquals(MessageStatus.PROCESSING , result.getStatus());

        assertNotNull(result.getProcessed_at());

        verify(messageHistoryFactory).create(message,MessageHistoryType.onConsume,"status changed to PROCESSING");

        verify(messageHistoryRepository).save(messageHistory);

    }

    @Test
    void shouldAknowledgeTheMessageSuccessfuly() {
    
        UUID id = UUID.randomUUID();
    
        AckRequest req = new AckRequest(id.toString());
    
        Message message = Message.builder().id(id).status(MessageStatus.PROCESSING).build();
    
        MessageHistory messageHistory = new MessageHistory();
    
        when(messageRepo.lockById(id)).thenReturn(Optional.of(message));
    
        when(messageHistoryFactory.create(message,MessageHistoryType.onACK,"message was acked by worker ")).thenReturn(messageHistory);
    
        consumerService.acknowledgeMessage(req);
    
        verify(messageRepo).lockById(id);
    
        verify(messageHistoryFactory).create(message,MessageHistoryType.onACK,"message was acked by worker ");
    
        verify(messageHistoryRepository).save(messageHistory);
    
        assertEquals(MessageStatus.ACKED, message.getStatus());

        assertNotNull(message.getProcessed_at());

    }

    @Test
    void shouldThrowWhenQueueNotFound() {   
        
        when(brokerQueueRepo.findByName("orders")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> consumerService.consume("orders"));

    }

    void shouldThrowWhenNoMessageAvailable() {


    }

    @Test
    void shouldThrowWhenMessageNotFound() {

        UUID id = UUID.randomUUID();

        when(messageRepo.lockById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> consumerService.acknowledgeMessage(new AckRequest(id.toString())));

    }

    @Test
    void shouldReturnWhenMessageAlreadyAcked() {

        UUID id = UUID.randomUUID();

        AckRequest ackRequest = new AckRequest(id.toString());

        Message message = new Message();
        
        message.setStatus(MessageStatus.ACKED);

        when(messageRepo.lockById(id)).thenReturn(Optional.of(message));

        consumerService.acknowledgeMessage(ackRequest);

        verify(messageHistoryFactory, never()).create(any(),any(),any());

        verify(messageHistoryRepository, never()).save(any());
        
    }

    @Test
    void shouldThrowWhenMessageIsNotProcessing() {

        UUID id = UUID.randomUUID();

        Message message = new Message();

        message.setStatus(MessageStatus.READY);

        when(messageRepo.lockById(id)).thenReturn(Optional.of(message));

        assertThrows(IllegalStateException.class,()-> consumerService.acknowledgeMessage(new AckRequest(id.toString())));

    }

}
