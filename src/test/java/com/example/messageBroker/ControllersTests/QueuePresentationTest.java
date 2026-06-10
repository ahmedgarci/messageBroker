package com.example.messageBroker.ControllersTests;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.messageBroker.controller.Queues.QueuesPresentation;
import com.example.messageBroker.controller.Queues.Resp.QueueResponse;
import com.example.messageBroker.core.BrokerQueuesService;
import com.example.messageBroker.domain.Message;

@WebMvcTest(QueuesPresentation.class)
@AutoConfigureMockMvc(addFilters = false)
public class QueuePresentationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BrokerQueuesService queuesService;

    private static final String ENDPOINT ="/queue";

    @Test
    public void shouldReturnAllQueuesSuccessfully() throws Exception{

        when(queuesService.getQueuees()).thenReturn(List.of(new QueueResponse()));

        mockMvc.perform(get(ENDPOINT)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1));

        verify(queuesService).getQueuees();

    }

    @Test
    public void shouldCreateQueueSuccessfully()throws Exception{
        String data = """
            {
                "name": "exchange1",
                "type": "DIRECT"
            }
        """;

            mockMvc.perform(post(ENDPOINT).content(data).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

            verify(queuesService).create(any());
    }

    @Test
    public void shouldReturnDeadLetterQueueMessages() throws Exception {
    
        String dlqName = "orders_dlq";
    
        when(queuesService.getDlqMessages(dlqName)).thenReturn(List.of(new Message()));
    
        mockMvc.perform(get(ENDPOINT + "/{dlqName}/messages", dlqName)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    
        verify(queuesService).getDlqMessages(dlqName);

    } 
    
}
