package com.example.messageBroker.core.Bindings;

import org.springframework.stereotype.Service;

import com.example.messageBroker.Mappers.Binding.BindingFactor;
import com.example.messageBroker.Repository.BindingRepo;
import com.example.messageBroker.Validations.Bindings.BindingValidator;
import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;
import com.example.messageBroker.domain.Binding;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BindingService {

    private final BindingValidator bindingValidator;
    private final BindingRepo bindingRepo;
    private final BindingFactor bindingFactory;

    @Transactional
    public void createBinding(CreateBindingRequest request) {

        bindingValidator.validate(request);

        Binding binding = bindingFactory.create(request);

        bindingRepo.save(binding);

    }
    
}
