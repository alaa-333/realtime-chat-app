package com.example.websocket.dto;

import lombok.*;

import java.nio.file.attribute.FileAttribute;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PrivateNotification {

    private String senderName;
    private String recipientName;
    private String content;
    private ChatMessage.MessageType type;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @Builder.Default
    private boolean isRead = false;
}
