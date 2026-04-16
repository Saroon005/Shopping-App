package com.group.shoppingapp.dto;

public class NotificationRequest {
    private String notificationType;
    private String recipientReference;
    private String message;

    public NotificationRequest() {}

    public NotificationRequest(String notificationType, String recipientReference, String message) {
        this.notificationType = notificationType;
        this.recipientReference = recipientReference;
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getRecipientReference() {
        return recipientReference;
    }

    public void setRecipientReference(String recipientReference) {
        this.recipientReference = recipientReference;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class NotificationResponse {
    private Long notificationId;
    private String notificationType;
    private String recipientReference;
    private String message;
    private String status;

    public NotificationResponse() {}

    public NotificationResponse(Long notificationId, String notificationType, String recipientReference, String message, String status) {
        this.notificationId = notificationId;
        this.notificationType = notificationType;
        this.recipientReference = recipientReference;
        this.message = message;
        this.status = status;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getRecipientReference() {
        return recipientReference;
    }

    public void setRecipientReference(String recipientReference) {
        this.recipientReference = recipientReference;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

