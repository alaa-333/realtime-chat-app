package com.example.websocket.dto;


import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ChatNotification {

    private String senderName;
    private String content;
    private ChatMessage.MessageType type;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
