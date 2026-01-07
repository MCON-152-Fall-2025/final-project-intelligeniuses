package com.mcon152.recipeshare.web;

import com.mcon152.recipeshare.domain.Notification;
import com.mcon152.recipeshare.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getNotifications(@RequestParam Long userId) {
        return notificationService.getNotificationsForUser(userId);
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications(@RequestParam Long userId) {
        return notificationService.getUnreadNotificationsForUser(userId);
    }

    @PutMapping("/{id}/read")
    public Notification readNotification(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }
}