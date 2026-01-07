package com.mcon152.recipeshare.repository;

import com.mcon152.recipeshare.domain.AppUser;
import com.mcon152.recipeshare.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // Find all follows where this user is the follower
    List<Follow> findByFollower(AppUser follower);

    // Find all follows where this user is being followed (these are their followers!)
    List<Follow> findByFollowing(AppUser following);

    // Find a specific follow relationship between two users
    Optional<Follow> findByFollowerAndFollowing(AppUser follower, AppUser following);

    // Delete a follow relationship
    void deleteByFollowerAndFollowing(AppUser follower, AppUser following);
}
