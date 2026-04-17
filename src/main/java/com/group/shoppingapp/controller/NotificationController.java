package com.group.shoppingapp.controller;

import com.group.shoppingapp.dto.NotificationRequest;
import com.group.shoppingapp.dto.NotificationResponse;
import com.group.shoppingapp.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	@Autowired
    private NotificationService service;


    @PostMapping
    public NotificationResponse createNotification(@RequestBody NotificationRequest request) {
        return service.createNotification(request);
    }

    @GetMapping
    public List<NotificationResponse> getAllNotifications() {
        return service.getAllNotifications();
    }

    @GetMapping("/{id}")
    public NotificationResponse getNotificationById(@PathVariable Long id) {
        return service.getNotificationById(id);
    }

}
