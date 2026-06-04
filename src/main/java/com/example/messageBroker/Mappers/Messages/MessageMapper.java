package com.example.messageBroker.Mappers.Messages;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.messageBroker.controller.Messages.Responses.DlqMessageResponse;
import com.example.messageBroker.controller.Messages.Responses.MessageResponse;
import com.example.messageBroker.domain.Message;

@Component
public class MessageMapper {
    
    
    public MessageResponse toMessageResponse(Object[] o){
        return MessageResponse.builder()
        .created_at((LocalDateTime)o[5])
        .message_id((UUID) o[0])
        .queue((String) o[1])
        .retries((Integer) o[4])
        .status((String)o[2])
        .type((String)o[3])
        .build();
    }

    public DlqMessageResponse fromMessagetoMessageFailedResponse(Message message){
        return DlqMessageResponse.builder()
        .message_id(message.getId())
        .queue(message.getQueue().getName())
        .retries(message.getRetryCount())
        .build();
    }


}
