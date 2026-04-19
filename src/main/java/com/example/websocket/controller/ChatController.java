package com.example.websocket.controller;


import com.example.websocket.dto.ChatMessage;
import com.example.websocket.dto.ChatNotification;
import com.example.websocket.dto.PrivateMessage;
import com.example.websocket.dto.PrivateNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    // Handles STOMP messages sent to destination: /app/chat.send
    // The /app prefix comes from your WebSocketConfig — Spring strips it before matching.
    @MessageMapping("/chat.send")
    // After this method returns, Spring serializes the return value to JSON
    // and broadcasts it to every client subscribed to /topic/public.
    @SendTo("/topic/public")
    public ChatNotification sendMessage(@Payload ChatMessage message) {


        return ChatNotification.builder()
                .senderName(message.getSenderName())
                .content(message.getContent())
                .type(message.getMessageType())
                .dateTime(LocalDateTime.now())
                .build();
    }


    @MessageMapping("/chat.join")
    @SendTo("/topic/public")
    public ChatNotification userJoined(@Payload ChatMessage message) {

        return ChatNotification.builder()
                .senderName(message.getSenderName())
                .content(message.getSenderName() + " joined the chat")
                .type(ChatMessage.MessageType.JOIN)
                .build();
    }

    @MessageMapping("/chat.leave")
    @SendTo("/topic/public")
    public ChatNotification userLeaved(@Payload ChatMessage message) {

        return ChatNotification.builder()
                .senderName(message.getSenderName())
                .content(message.getSenderName() + " leaved the chat")
                .type(ChatMessage.MessageType.LEAVE)
                .build();
    }

    @MessageMapping("/chat.typing")
    @SendTo("/topic/public")
    public ChatNotification userTyping(@Payload ChatMessage message) {

        return ChatNotification.builder()
                .senderName(message.getSenderName())
                .type(ChatMessage.MessageType.TYPING)
                .build();
    }


    @MessageMapping("/chat.private")
    public void toPrivateMsg(@Payload PrivateMessage message) {

        // build notification obj
        var notification =  PrivateNotification.builder()
                .senderName(message.getSenderName())
                .recipientName(message.getRecipientName())
                .content(message.getContent())
                .type(ChatMessage.MessageType.CHAT)
                .build();

        // Deliver ONLY to the recipient.
        // Spring resolves "bob" + "/queue/messages" → /user/bob/queue/messages
        // and delivers to every session belonging to user "bob".
        messagingTemplate.convertAndSendToUser(
                message.getRecipientName(),   // the target username
                "/queue/messages",            // the destination suffix
                notification                  // the payload (serialized to JSON)
        );

        // Also send a copy back to the sender so their own UI reflects
        // the sent message (a common UX pattern in chat applications).
        messagingTemplate.convertAndSendToUser(
                message.getSenderName(),      // back to the sender too
                "/queue/messages",
                notification
        );
    }
}
