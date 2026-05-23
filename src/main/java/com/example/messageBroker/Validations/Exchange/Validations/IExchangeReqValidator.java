package com.example.messageBroker.Validations.Exchange.Validations;

import com.example.messageBroker.controller.Exchange.Requestss.CreateExchangeReq;

public interface IExchangeReqValidator {
    public void validate(CreateExchangeReq req);
}
