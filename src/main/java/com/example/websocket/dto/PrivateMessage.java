package com.example.websocket.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PrivateMessage {

    private String senderName;
    private String recipientName;
    private String content;


}
