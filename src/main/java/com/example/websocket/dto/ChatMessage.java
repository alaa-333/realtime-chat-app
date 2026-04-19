package com.example.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ChatMessage {

    private String senderName;
    private String content;
    private MessageType messageType;

    // The type tells the UI how to render this message.
    // A CHAT message gets a speech bubble.
    // A JOIN or LEAVE message gets a system notification banner.
    // A TYPING message shows typing indicator.
    public enum MessageType {
        CHAT, JOIN, LEAVE, TYPING
    }
}
