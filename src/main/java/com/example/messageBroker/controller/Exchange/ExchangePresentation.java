package com.example.messageBroker.controller.Exchange;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messageBroker.controller.Exchange.Requestss.CreateExchangeReq;
import com.example.messageBroker.core.Exchange.ExchangeService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/exchange")
@RestController
@RequiredArgsConstructor
public class ExchangePresentation {
    
    private final ExchangeService exchangeService;
    
    @PostMapping
    public void createExchange(@RequestBody CreateExchangeReq req) {        
        exchangeService.createExchange(req);
    }
    
}
