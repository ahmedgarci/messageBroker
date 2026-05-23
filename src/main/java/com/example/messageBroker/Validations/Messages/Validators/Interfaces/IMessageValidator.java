package com.example.messageBroker.Validations.Messages.Validators.Interfaces;

import com.example.messageBroker.controller.Producers.Requests.PublishRequest;

public interface IMessageValidator {
        void validate(PublishRequest request);

}
