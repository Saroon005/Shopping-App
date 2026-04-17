package com.group.shoppingapp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Notification() {
    	this.createdAt = LocalDateTime.now();
    }

    public Notification(Long notificationId, String notificationType, String recipientReference, String message, String status, User user) {
        this.notificationId = notificationId;
        this.notificationType = notificationType;
        this.recipientReference = recipientReference;
        this.message = message;
        this.status = status;
        this.user = user;
    	this.createdAt = LocalDateTime.now();
        
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
