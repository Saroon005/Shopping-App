package com.group.shoppingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequest {
    @NotBlank(message = "Notification type cannot be empty")
    private String notificationType;
    
    @NotBlank(message = "Message cannot be empty")
    private String message;
    
    @NotNull(message = "User ID cannot be null")
    private Long user_id;

    public NotificationRequest() {}

    public NotificationRequest(String notificationType, Long user_id, String message) {
        this.notificationType = notificationType;
        this.message = message;
        this.user_id = user_id;        		
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    

    public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}



