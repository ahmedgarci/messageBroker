package com.example.messageBroker.core.Exchange;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.messageBroker.Mappers.Exchange.ExchangeFactory;
import com.example.messageBroker.Mappers.Exchange.ExchangeMapper;
import com.example.messageBroker.Repository.ExchnageRepo;
import com.example.messageBroker.Validations.Exchange.ExchangeValidator;
import com.example.messageBroker.controller.Exchange.Requestss.CreateExchangeReq;
import com.example.messageBroker.controller.Exchange.Responses.ExchangeDTO;
import com.example.messageBroker.controller.Exchange.Responses.ExchangeResponse;
import com.example.messageBroker.domain.Exchange;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    
    private final ExchnageRepo exchangeRepo;
    private final ExchangeValidator exchangeValidator;
    private final ExchangeFactory exchangeFactory;
    private final ExchangeMapper exchangeMapper;

    @Transactional
    public void createExchange(CreateExchangeReq req){

        exchangeValidator.validate(req);

        Exchange exchange =  exchangeFactory.create(req);

        exchangeRepo.save(exchange);
    }

    public List<ExchangeResponse> getExchangesDetails(){
        
        List<Object[]> exchanges = exchangeRepo.findExchangeStats();

        return exchanges.stream().map((e)-> exchangeMapper.toExchangeResponse(e)).toList();
    
    }

    public List<?> getAllExchanges() {
        
        return exchangeRepo.findAll().stream().map((e)-> new ExchangeDTO(e.getId(),e.getName())).toList();

    }

}
