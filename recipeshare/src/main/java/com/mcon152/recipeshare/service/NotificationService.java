package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.Notification;
import com.mcon152.recipeshare.events.RecipeCreatedEvent;
import com.mcon152.recipeshare.repository.AppUserRepository;
import com.mcon152.recipeshare.repository.NotificationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    // dependencies
    private final NotificationRepository notificationRepository;
    private final FollowService followService;
    private final AppUserRepository appUserRepository;

    // constructor
    public NotificationService(NotificationRepository notificationRepository, FollowService followService, AppUserRepository appUserRepository) {
        this.notificationRepository = notificationRepository;
        this.followService = followService;
        this.appUserRepository = appUserRepository;
    }

    // observer - listens for recipe events
    @EventListener
    public void handleRecipeCreated(RecipeCreatedEvent event) {

        // declare that notification event is received
        System.out.println("*** NOTIFICATION EVENT RECEIVED ***");
        System.out.println("Recipe Title: " + event.getRecipeTitle());
        System.out.println("Author ID: " + event.getAuthorId());

        // get recipe author
        Long authorId = event.getAuthorId();

        // get followers of this author
        List<Long> followerIds = followService.getFollowers(authorId);

        // create notification message
        String message = "New recipe '" + event.getRecipeTitle() + "' published!";

        // create a notification for each follower
        for (Long followerId : followerIds) {
            Notification notification = new Notification(followerId, message);
            notificationRepository.save(notification);
        }

        // declare that notification processing is complete
        System.out.println("=== NOTIFICATION PROCESSING COMPLETE ===");
        System.out.println();
    }

    public List<Notification> getNotificationsForUser(Long userId) {
       if(!appUserRepository.existsById(userId)) {
            throw new RuntimeException("No such user " + userId);
        }
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> getUnreadNotificationsForUser(Long userId) {
        if(!appUserRepository.existsById(userId)) {
            throw new RuntimeException("No such user " + userId);
        }
        return notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
    }

    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        return notificationRepository.save(notification);
    }
}
