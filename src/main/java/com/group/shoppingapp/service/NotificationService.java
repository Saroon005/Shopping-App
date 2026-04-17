package com.group.shoppingapp.service;

import com.group.shoppingapp.dto.NotificationRequest;
import com.group.shoppingapp.dto.NotificationResponse;
import com.group.shoppingapp.entity.Notification;
import com.group.shoppingapp.entity.User;
import com.group.shoppingapp.repository.NotificationRepository;
import com.group.shoppingapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private NotificationRepository repository;
    
    public NotificationResponse createNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setNotificationType(request.getNotificationType());
        
        User user = userRepo.findById(request.getUser_id()).orElse(null);
        notification.setUser(user);
        notification.setMessage(request.getMessage());
        notification.setStatus("PENDING");

        Notification saved = repository.save(notification);
        return mapToResponse(saved);
    }

    public List<NotificationResponse> getAllNotifications() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public NotificationResponse getNotificationById(Long id) {
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }


    private NotificationResponse mapToResponse(Notification notification) {
        return new NotificationResponse(
                notification.getNotificationId(),
                notification.getNotificationType(),
                notification.getMessage(),
                notification.getStatus()
        );
    }
}
