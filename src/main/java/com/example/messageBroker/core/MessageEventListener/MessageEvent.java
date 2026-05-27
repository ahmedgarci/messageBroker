package com.example.messageBroker.core.MessageEventListener;

import com.example.messageBroker.domain.Message;

public record MessageEvent(
    Message message
) {
    
}
