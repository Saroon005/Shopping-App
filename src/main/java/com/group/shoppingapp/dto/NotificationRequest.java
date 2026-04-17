package com.group.shoppingapp.dto;

public class NotificationRequest {
    private String notificationType;
    private String message;
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



