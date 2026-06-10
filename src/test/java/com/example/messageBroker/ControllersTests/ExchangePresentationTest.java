package com.example.messageBroker.ControllersTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.example.messageBroker.controller.Exchange.ExchangePresentation;
import com.example.messageBroker.controller.Exchange.Responses.ExchangeDTO;
import com.example.messageBroker.controller.Exchange.Responses.ExchangeResponse;
import com.example.messageBroker.core.Exchange.ExchangeService;

@WebMvcTest(ExchangePresentation.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExchangePresentationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService exchangeService;

    private final static String ENDPOINT="/exchange";

    @Test
    public void shouldCreateExchangeSuccessfully() throws Exception{
        String json = """
            {
                "name": "exchange1",
                "type": "DIRECT"
            }
        """;
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());      

        verify(exchangeService).createExchange((any()));

    }

    @Test
    public void shouldReturnExchangeStats()throws Exception{

        List<ExchangeResponse> response = List.of(new ExchangeResponse());

        when(exchangeService.getExchangesDetails()).thenReturn(response);

        mockMvc.perform(get(ENDPOINT)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1));

        verify(exchangeService).getExchangesDetails();
    }

    @Test
    void shouldReturnEmptyExchangeStats() throws Exception {

        when(exchangeService.getExchangesDetails()).thenReturn(List.of());

        mockMvc.perform(get(ENDPOINT)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));

        verify(exchangeService).getExchangesDetails();

    }

    @Test
    public void shouldReturnAllExchanges() throws Exception{
        
        List<ExchangeDTO> response = List.of(new ExchangeDTO());

        when(exchangeService.getAllExchanges()).thenReturn(response);

        mockMvc.perform(get(ENDPOINT+"/all")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1));

        verify(exchangeService).getAllExchanges();

    }

    @Test
    public void shouldReturnEmptyExchanges()throws Exception{

        when(exchangeService.getAllExchanges()).thenReturn(List.of());

        mockMvc.perform(get(ENDPOINT+"/all")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));

        verify(exchangeService).getAllExchanges();
    }

}
