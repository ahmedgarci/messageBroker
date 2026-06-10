package com.example.messageBroker.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.messageBroker.Repository.BindingRepo;
import com.example.messageBroker.Repository.ExchnageRepo;
import com.example.messageBroker.Repository.MessageRepo;
import com.example.messageBroker.Validations.Messages.ValidationPipeline;
import com.example.messageBroker.controller.Producers.Requests.PublishRequest;
import com.example.messageBroker.core.ProducerService;
import com.example.messageBroker.core.RoutingService;
import com.example.messageBroker.domain.Binding;
import com.example.messageBroker.domain.BrokerQueue;
import com.example.messageBroker.domain.Exchange;
import com.example.messageBroker.domain.Message;
import com.example.messageBroker.domain.Constants.MessageStatus;
import com.example.messageBroker.domain.Constants.MessageType;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProducerServiceTest {
    @Mock
    private  BindingRepo bindingRepo;
    @Mock
    private  ExchnageRepo exchangeRepo;
    @Mock
    private  ValidationPipeline validationPipeline;
    @Mock
    private  RoutingService routingService;
    @Mock
    private  MessageRepo messageRepo;

    @InjectMocks
    private ProducerService producerService;

    @Test
    void shouldPublishMessageSuccessfully() {

        PublishRequest request = new PublishRequest( "hello",MessageType.TEXT,Map.of(),"orders.created",1);
        
        Exchange exchange = Exchange.builder().id(UUID.randomUUID()).build();

        List<Binding> bindings = List.of(new Binding());

        when(exchangeRepo.findByName("exchangeFakeName")).thenReturn(Optional.of(exchange));

        when(bindingRepo.findByExchangeAndRoutingKey(exchange.getId())).thenReturn(bindings);

        producerService.publish("exchangeFakeName", request);

        verify(validationPipeline).validate(request);

        verify(exchangeRepo).findByName("exchangeFakeName");

        verify(bindingRepo).findByExchangeAndRoutingKey(exchange.getId());

        verify(routingService).route(request, bindings, exchange);
        
    }
    
    @Test
    void shouldThrowWhenExchangeNotFound() {

    PublishRequest request = new PublishRequest( "hello",MessageType.TEXT,Map.of(),"orders.created",1);

    when(exchangeRepo.findByName("orders")).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,() -> producerService.publish("orders", request));

    verify(routingService, never()).route(any(), any(), any());
    
    }

    @Test
    void shouldStopWhenValidationFails() {

        PublishRequest request = new PublishRequest( "hello",MessageType.TEXT,Map.of(),"orders.created",1);

        doThrow(new IllegalArgumentException("")).when(validationPipeline).validate(request);

        assertThrows(IllegalArgumentException.class, ()-> producerService.publish("fakeName", request));

        verify(exchangeRepo, never()).findByName(any());

        verify(routingService, never()).route(any(), any(), any());

    }

    @Test
    void shouldRecoverMessageSuccessfully() {

        UUID id = UUID.randomUUID();

        BrokerQueue queue = new BrokerQueue();

        Message message = Message.builder().originialQueue(queue).retryCount(5).processed_at(LocalDateTime.now()).build();

        when(messageRepo.findById(id)).thenReturn(Optional.of(message));

        producerService.recoverMessage(id.toString());

        assertEquals(MessageStatus.READY,message.getStatus());

        assertEquals(queue, message.getQueue());

        assertNull(message.getProcessed_at());

        assertEquals(0, message.getRetryCount());

    }

    @Test
    void shouldThrowWhenMessageNotFound() {
        
        UUID id = UUID.randomUUID();

        when(messageRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->producerService.recoverMessage(id.toString()));


    }

    @Test
    void shouldThrowWhenUuidIsInvalid() {

    assertThrows(IllegalArgumentException.class,() -> producerService.recoverMessage("abc"));

    }




}
