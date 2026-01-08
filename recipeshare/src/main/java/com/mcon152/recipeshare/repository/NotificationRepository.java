package com.mcon152.recipeshare.repository;

import com.mcon152.recipeshare.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // find all notifications for a specific user, sorted by newest first
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    // find all unread notifications for a specific user, sorted by newest first
    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);

}
