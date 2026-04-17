package com.group.shoppingapp.dto;


public class NotificationResponse {
    private Long notificationId;
    private String notificationType;
    private String message;
    private String status;

    public NotificationResponse() {}

    public NotificationResponse(Long notificationId, String notificationType,String message, String status) {
        this.notificationId = notificationId;
        this.notificationType = notificationType;
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