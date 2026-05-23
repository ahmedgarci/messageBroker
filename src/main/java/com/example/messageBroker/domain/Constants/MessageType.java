package com.example.messageBroker.domain.Constants;

public enum MessageType {
    TEXT,JSON,BINARY;


    public static MessageType fromString(String value) {
        return MessageType.valueOf(value.toUpperCase());
    }
}
