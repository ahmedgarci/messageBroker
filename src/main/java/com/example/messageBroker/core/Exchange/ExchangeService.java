package com.example.messageBroker.core.Exchange;

import org.springframework.stereotype.Service;

import com.example.messageBroker.Mappers.Exchange.ExchangeFactory;
import com.example.messageBroker.Repository.ExchnageRepo;
import com.example.messageBroker.Validations.Exchange.ExchangeValidator;
import com.example.messageBroker.controller.Exchange.Requestss.CreateExchangeReq;
import com.example.messageBroker.domain.Exchange;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    
    private final ExchnageRepo exchangeRepo;
    private final ExchangeValidator exchangeValidator;
    private final ExchangeFactory exchangeFactory;

    @Transactional
    public void createExchange(CreateExchangeReq req){

        exchangeValidator.validate(req);

        Exchange exchange =  exchangeFactory.create(req);

        exchangeRepo.save(exchange);
    }
}
