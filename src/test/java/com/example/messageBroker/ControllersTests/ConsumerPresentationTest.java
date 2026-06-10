package com.example.messageBroker.ControllersTests;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.messageBroker.controller.Consumer.ConsumerPresentation;
import com.example.messageBroker.core.ConsumerService;
import com.example.messageBroker.core.MessageService;
import com.example.messageBroker.domain.Message;

@WebMvcTest(ConsumerPresentation.class)
@AutoConfigureMockMvc(addFilters = false)
public class ConsumerPresentationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsumerService consumerService;

    @MockBean
    private MessageService messageService;

    @Test
    public void shouldConsumeMessageSuccessfully()throws Exception{

        String queueName = "orders";

        when(consumerService.consume(queueName)).thenReturn(new Message());

        mockMvc.perform(get("/queues/{queueName}/messages/next",queueName)).andExpect(status().isOk());

        verify(consumerService).consume(queueName);

    }

    

}
