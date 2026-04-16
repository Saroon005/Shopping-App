package com.group.shoppingapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
    private String notificationType;
    private String recipientReference;
    private String message;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class NotificationResponse {
    private Long notificationId;
    private String notificationType;
    private String recipientReference;
    private String message;
    private String status;
}
