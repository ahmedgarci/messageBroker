package com.example.messageBroker.controller.Bindings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messageBroker.controller.Bindings.Requests.CreateBindingRequest;
import com.example.messageBroker.core.Bindings.BindingService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/binding")
@RequiredArgsConstructor
public class BindingPresentation {

    private final BindingService bindingService;

    @PostMapping
    public void postBinding(@RequestBody CreateBindingRequest request) {
        bindingService.createBinding(request);
    }
    
}
