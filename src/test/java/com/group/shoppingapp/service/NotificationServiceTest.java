package com.group.shoppingapp.service;

import com.group.shoppingapp.dto.NotificationRequest;
import com.group.shoppingapp.dto.NotificationResponse;
import com.group.shoppingapp.entity.Notification;
import com.group.shoppingapp.entity.User;
import com.group.shoppingapp.repository.NotificationRepository;
import com.group.shoppingapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    private Notification testNotification;
    private NotificationRequest notificationRequest;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john@example.com");

        testNotification = new Notification();
        testNotification.setNotificationId(1L);
        testNotification.setNotificationType("CHECKOUT_CONFIRMATION");
        testNotification.setMessage("Your order has been confirmed");
        testNotification.setStatus("PENDING");
        testNotification.setUser(testUser);
        testNotification.setCreatedAt(LocalDateTime.now());

        notificationRequest = new NotificationRequest();
        notificationRequest.setUser_id(1L);
        notificationRequest.setNotificationType("CHECKOUT_CONFIRMATION");
        notificationRequest.setMessage("Your order has been confirmed");
    }

    @Test
    void testCreateNotification_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        NotificationResponse result = notificationService.createNotification(notificationRequest);

        assertNotNull(result);
        assertEquals("CHECKOUT_CONFIRMATION", result.getNotificationType());
        assertEquals("Your order has been confirmed", result.getMessage());
        assertEquals("PENDING", result.getStatus());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testCreateNotification_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        NotificationResponse result = notificationService.createNotification(notificationRequest);

        assertNotNull(result);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testCreateNotification_NullRequest() {
        assertThrows(NullPointerException.class, () -> {
            notificationService.createNotification(null);
        });

        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void testCreateNotification_LowStockNotification() {
        NotificationRequest lowStockRequest = new NotificationRequest();
        lowStockRequest.setUser_id(1L);
        lowStockRequest.setNotificationType("LOW_STOCK");
        lowStockRequest.setMessage("Product stock is low");

        Notification lowStockNotification = new Notification();
        lowStockNotification.setNotificationId(2L);
        lowStockNotification.setNotificationType("LOW_STOCK");
        lowStockNotification.setMessage("Product stock is low");
        lowStockNotification.setStatus("PENDING");
        lowStockNotification.setUser(testUser);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(notificationRepository.save(any(Notification.class))).thenReturn(lowStockNotification);

        NotificationResponse result = notificationService.createNotification(lowStockRequest);

        assertNotNull(result);
        assertEquals("LOW_STOCK", result.getNotificationType());
        assertEquals("PENDING", result.getStatus());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testCreateNotification_DefaultStatusIsPending() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
            Notification notif = invocation.getArgument(0);
            assertEquals("PENDING", notif.getStatus());
            return notif;
        });

        NotificationResponse result = notificationService.createNotification(notificationRequest);

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void testGetAllNotifications_Success() {
        Notification notification2 = new Notification();
        notification2.setNotificationId(2L);
        notification2.setNotificationType("LOW_STOCK");
        notification2.setMessage("Stock is low");
        notification2.setStatus("SENT");
        notification2.setUser(testUser);

        List<Notification> notifications = Arrays.asList(testNotification, notification2);
        when(notificationRepository.findAll()).thenReturn(notifications);

        List<NotificationResponse> result = notificationService.getAllNotifications();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CHECKOUT_CONFIRMATION", result.get(0).getNotificationType());
        assertEquals("LOW_STOCK", result.get(1).getNotificationType());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void testGetAllNotifications_Empty() {
        when(notificationRepository.findAll()).thenReturn(Arrays.asList());

        List<NotificationResponse> result = notificationService.getAllNotifications();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void testGetNotificationById_Found() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(testNotification));

        NotificationResponse result = notificationService.getNotificationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getNotificationId());
        assertEquals("CHECKOUT_CONFIRMATION", result.getNotificationType());
        assertEquals("Your order has been confirmed", result.getMessage());
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetNotificationById_NotFound() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            notificationService.getNotificationById(999L);
        });

        verify(notificationRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateNotification_MultipleNotifications() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        for (int i = 0; i < 5; i++) {
            NotificationRequest request = new NotificationRequest();
            request.setUser_id(1L);
            request.setNotificationType("NOTIFICATION_TYPE_" + i);
            request.setMessage("Message " + i);

            NotificationResponse result = notificationService.createNotification(request);
            assertNotNull(result);
        }

        verify(notificationRepository, times(5)).save(any(Notification.class));
    }

    @Test
    void testGetAllNotifications_MultipleUsers() {
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");

        Notification notification2 = new Notification();
        notification2.setNotificationId(2L);
        notification2.setNotificationType("CHECKOUT_CONFIRMATION");
        notification2.setMessage("Jane's order confirmed");
        notification2.setStatus("SENT");
        notification2.setUser(user2);

        List<Notification> notifications = Arrays.asList(testNotification, notification2);
        when(notificationRepository.findAll()).thenReturn(notifications);

        List<NotificationResponse> result = notificationService.getAllNotifications();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void testCreateNotification_MapsCorrectly() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
            Notification notif = invocation.getArgument(0);
            notif.setNotificationId(1L);
            return notif;
        });

        NotificationResponse result = notificationService.createNotification(notificationRequest);

        assertNotNull(result);
        assertEquals(notificationRequest.getNotificationType(), result.getNotificationType());
        assertEquals(notificationRequest.getMessage(), result.getMessage());
    }

    @Test
    void testGetNotificationById_WithDifferentStatuses() {
        Notification sentNotification = new Notification();
        sentNotification.setNotificationId(1L);
        sentNotification.setStatus("SENT");
        sentNotification.setNotificationType("CHECKOUT_CONFIRMATION");
        sentNotification.setMessage("Order confirmed");
        sentNotification.setUser(testUser);

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(sentNotification));

        NotificationResponse result = notificationService.getNotificationById(1L);

        assertNotNull(result);
        assertEquals("SENT", result.getStatus());
    }

    @Test
    void testCreateNotification_PreservesUserReference() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
            Notification notif = invocation.getArgument(0);
            notif.setNotificationId(1L);
            notif.setUser(testUser);
            return notif;
        });

        NotificationResponse result = notificationService.createNotification(notificationRequest);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(1L);
    }
}
