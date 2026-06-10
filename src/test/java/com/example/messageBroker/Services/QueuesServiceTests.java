package com.example.messageBroker.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.messageBroker.Mappers.Queue.BrodkerQueueCreator;
import com.example.messageBroker.Repository.BrokerQueueRepo;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.controller.Queues.Requests.CreateQueueRequest;
import com.example.messageBroker.controller.Queues.Resp.QueueResponse;
import com.example.messageBroker.core.BrokerQueuesService;
import com.example.messageBroker.domain.BrokerQueue;
import com.example.messageBroker.domain.Message;
import com.example.messageBroker.domain.Constants.MessageStatus;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class QueuesServiceTests {

    @Mock
    private BrokerQueueRepo brokerQueueRepo;

    @Mock
    private BrodkerQueueCreator brokerQueueCreator;

    @Mock
    private MessageRepo messageRepo;

    @InjectMocks
    private BrokerQueuesService brokerQueuesService;


    @Test
    void shouldCreateQueueAndDlqIfNotExists() {
    
        CreateQueueRequest request = new CreateQueueRequest("orders");
    
        BrokerQueue dlq = BrokerQueue.builder()
                .id(UUID.randomUUID())
                .name("orders_dlq")
                .build();
    
        BrokerQueue mainQueue = BrokerQueue.builder()
                .id(UUID.randomUUID())
                .name("orders")
                .build();
    
        // 1. main queue does not exist
        when(brokerQueueRepo.findByName("orders"))
                .thenReturn(Optional.empty());
    
        // 2. dlq does not exist
        when(brokerQueueRepo.findByName("orders_dlq"))
                .thenReturn(Optional.empty());
    
        // 3. dlq creation
        when(brokerQueueCreator.createDlq("orders_dlq"))
                .thenReturn(dlq);
    
        when(brokerQueueRepo.save(dlq))
                .thenReturn(dlq);
    
        // 4. main queue creation
        when(brokerQueueCreator.create(request, dlq))
                .thenReturn(mainQueue);
    
        when(brokerQueueRepo.save(mainQueue))
                .thenReturn(mainQueue);
    
        // ACT
        brokerQueuesService.create(request);
    
        // ASSERT
        verify(brokerQueueRepo).findByName("orders");
        verify(brokerQueueRepo).findByName("orders_dlq");
    
        verify(brokerQueueCreator).createDlq("orders_dlq");
        verify(brokerQueueRepo).save(dlq);
    
        verify(brokerQueueCreator).create(request, dlq);
        verify(brokerQueueRepo).save(mainQueue);
    }
        @Test
        void shouldThrowIfQueueAlreadyExists() {
                CreateQueueRequest request = new CreateQueueRequest("orders");

                when(brokerQueueRepo.findByName("orders")).thenReturn(Optional.of(new BrokerQueue()));

                assertThrows(IllegalStateException.class, ()->brokerQueuesService.create(request));
        }

    @Test
    void shouldReturnDlqMessages(){
        
        BrokerQueue queue = BrokerQueue.builder().id(UUID.randomUUID()).name("orders_dlq").build();

        Message msg = new Message();


        when(brokerQueueRepo.findByName("orders_dlq")).thenReturn(Optional.of(queue));

        when(messageRepo.findByQueueIdAndStatus(queue.getId(),MessageStatus.FAILED)).thenReturn(List.of(msg));

        List<Message> failedMessages = brokerQueuesService.getDlqMessages("orders_dlq");

        assertEquals(1, failedMessages.size());
    }
        @Test
        void shouldThrowWhenDlqNotFound() {
                
                when(brokerQueueRepo.findByName("orders_dlq")).thenReturn(Optional.empty());

                assertThrows(EntityNotFoundException.class, ()-> brokerQueuesService.getDlqMessages("orders_dlq"));
        }

        @Test
        void shouldReturnEmptyDlqMessages() {
                BrokerQueue queue = BrokerQueue.builder().id(UUID.randomUUID()).name("orders_dlq").build();

                when(brokerQueueRepo.findByName("orders_dlq")).thenReturn(Optional.of(queue));

                when(messageRepo.findByQueueIdAndStatus(queue.getId(),MessageStatus.FAILED)).thenReturn(List.of());

                List<Message> result = brokerQueuesService.getDlqMessages(queue.getName());

                assertTrue(result.isEmpty());

        }


    @Test
    void shouldReturnAllQueues(){
        List<Object[]> rows = new ArrayList<>();
        rows.add(new Object[]{"orders",10L,5L,3L});
        
        when(brokerQueueRepo.findAllWithStats())
                .thenReturn(rows);
        
        List<QueueResponse> response = brokerQueuesService.getQueuees();

  assertEquals(1, response.size());

    QueueResponse queue = response.get(0);

    assertEquals("orders", queue.getName());
    assertEquals(10L, queue.getReady());
    assertEquals(5L, queue.getProcessing());
    assertEquals(2, queue.getTotalMessages());

    }






}