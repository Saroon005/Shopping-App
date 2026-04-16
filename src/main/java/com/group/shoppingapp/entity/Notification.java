package com.group.shoppingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String notificationType; // CHECKOUT_CONFIRMATION, LOW_STOCK
    private String recipientReference; // userId or admin reference
    private String message;
    private String status; // SENT, PENDING, FAILED

    public Notification() {}

    public Notification(Long notificationId, String notificationType, String recipientReference, String message, String status) {
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
