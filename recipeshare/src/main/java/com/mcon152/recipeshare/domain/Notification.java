package com.mcon152.recipeshare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Notification extends BaseEntity {
    // fields
    @Column(nullable = false)   // this field is required
    private Long userId;   // id of user of notification

    @Column(nullable = false, length = 500) // this field is required, up to 500 char
    private String message;                 // notification message

    @Column(nullable = false)   // this field is required
    private boolean isRead;     // has the user seen it

    // full constructor
    public Notification(Long userId, String message, boolean isRead) {
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
    }

    // simple constructor
    public Notification(Long userId, String message) {
        this.userId = userId;
        this.message = message;
        this.isRead = false;
    }

    // empty constructor
    public Notification() {}

    // getters
    public Long getUserId() {
        return userId;
    }
    public String getMessage() {
        return message;
    }
    public boolean isRead() {
        return isRead;
    }

    // setters
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
