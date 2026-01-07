package com.mcon152.recipeshare.domain;
import jakarta.persistence.*;

@Entity
@Table(name = "follows")
public class Follow extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private AppUser follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private AppUser following;

    // No args constructor
    public Follow() {
    }
    // Full constructor
    public Follow(AppUser follower, AppUser following) {
        this.follower = follower;
        this.following = following;
    }

    // Setters and Getters
    public AppUser getFollower() {
        return follower;
    }
    public void setFollower(AppUser follower) {
        this.follower = follower;
    }
    public AppUser getFollowing() {
        return following;
    }
    public void setFollowing(AppUser following) {
        this.following = following;
    }
}
