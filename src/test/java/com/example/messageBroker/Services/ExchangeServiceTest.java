package com.example.messageBroker.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.messageBroker.Mappers.Exchange.ExchangeFactory;
import com.example.messageBroker.Mappers.Exchange.ExchangeMapper;
import com.example.messageBroker.Repository.ExchnageRepo;
import com.example.messageBroker.Validations.Exchange.ExchangeValidator;
import com.example.messageBroker.controller.Exchange.Requestss.CreateExchangeReq;
import com.example.messageBroker.controller.Exchange.Responses.ExchangeResponse;
import com.example.messageBroker.core.Exchange.ExchangeService;
import com.example.messageBroker.domain.Exchange;
import com.example.messageBroker.domain.Constants.ExchangeType;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {
    
    @Mock
    private  ExchnageRepo exchangeRepo;
    @Mock
    private  ExchangeValidator exchangeValidator;
    @Mock
    private  ExchangeFactory exchangeFactory;
    @Mock
    private  ExchangeMapper exchangeMapper;

    @InjectMocks
    private ExchangeService exchangeService;

    @Test
    public void shouldCreateExchangeSuccessfully(){
        
        CreateExchangeReq req = new CreateExchangeReq("exchange_1",ExchangeType.DIRECT);
        
        Exchange exchange = new Exchange();

        when(exchangeFactory.create(req)).thenReturn(exchange);

        when(exchangeRepo.save(exchange)).thenReturn(exchange);

        exchangeService.createExchange(req);

        verify(exchangeValidator).validate(req);

        verify(exchangeRepo).save(exchange);

    }

    @Test
    public void shouldThrowWhenValidationFails(){

        CreateExchangeReq req = new CreateExchangeReq("exchange_1",ExchangeType.DIRECT);

        doThrow(IllegalArgumentException.class).when(exchangeValidator).validate(req);

        assertThrows(IllegalArgumentException.class, ()->exchangeService.createExchange(req));

        verify(exchangeFactory, never()).create(any());
        
        verify(exchangeRepo, never()).save(any());

    }

    @Test
    void shouldReturnExchangeStats() {

        Object[] row = new Object[]{"exchange1", 5L};

        List<Object[]> dbResult = new ArrayList<>();

        dbResult.add(row);

        ExchangeResponse response = new ExchangeResponse();

        when(exchangeRepo.findExchangeStats()).thenReturn(dbResult);

        when(exchangeMapper.toExchangeResponse(row)).thenReturn(response);

        List<ExchangeResponse> result = exchangeService.getExchangesDetails();

        assertEquals(1, result.size());

        verify(exchangeRepo).findExchangeStats();

        verify(exchangeMapper).toExchangeResponse(row);

    }

    @Test
    void shouldReturnAllExchanges() {

    Exchange ex1 = new Exchange();
    ex1.setName("ex1");

    Exchange ex2 = new Exchange();
    ex2.setName("ex2");

    when(exchangeRepo.findAll()).thenReturn(List.of(ex1, ex2));

    List<?> result = exchangeService.getAllExchanges();

    assertEquals(2, result.size());

    verify(exchangeRepo).findAll();
}

}
