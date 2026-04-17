package com.group.shoppingapp.service;

import com.group.shoppingapp.dto.NotificationRequest;
import com.group.shoppingapp.dto.NotificationResponse;
import com.group.shoppingapp.entity.Notification;
import com.group.shoppingapp.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public NotificationResponse createNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setNotificationType(request.getNotificationType());
        notification.setRecipientReference(request.getRecipientReference());
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

    public List<NotificationResponse> getNotificationsByUser(String userId) {
        return repository.findByRecipientReference(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return new NotificationResponse(
                notification.getNotificationId(),
                notification.getNotificationType(),
                notification.getRecipientReference(),
                notification.getMessage(),
                notification.getStatus()
        );
    }
}
