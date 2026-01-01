package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.AppUser;
import com.mcon152.recipeshare.domain.Follow;
import com.mcon152.recipeshare.repository.AppUserRepository;
import com.mcon152.recipeshare.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final AppUserRepository appUserRepository;

    // Constructor
    public FollowService(FollowRepository followRepository, AppUserRepository appUserRepository) {
        this.followRepository = followRepository;
        this.appUserRepository = appUserRepository;
    }
    public boolean follow(Long followerId, Long followingId) {
        Optional<AppUser> followerOpt = appUserRepository.findById(followerId);
        Optional<AppUser> followingOpt = appUserRepository.findById(followingId);

        if (followerOpt.isEmpty() || followingOpt.isEmpty()) {
            return false;
        }
        AppUser follower = followerOpt.get();
        AppUser following = followingOpt.get();

        // Check they're not trying to follow themselves
        if (followerId.equals(followingId)) {
            return false;
        }
        // Check they're not already following
        Optional<Follow> existing = followRepository.findByFollowerAndFollowing(follower, following);
        if (existing.isPresent()) {
            return false;
        }
        // Create and save new follow relationship
        Follow newFollow = new Follow(follower, following);
        followRepository.save(newFollow);
        return true;
    }

    // Remove a follow relationship
    public boolean unfollow(Long followerId, Long followingId) {
        Optional<AppUser> followerOpt = appUserRepository.findById(followerId);
        Optional<AppUser> followingOpt = appUserRepository.findById(followingId);

        if (followerOpt.isEmpty() || followingOpt.isEmpty()) {
            return false;
        }
        followRepository.deleteByFollowerAndFollowing(followerOpt.get(), followingOpt.get());
        return true;
    }

    // Get list of user IDs who follow this author
    public List<Long> getFollowers(Long authorId) {
        Optional<AppUser> authorOpt = appUserRepository.findById(authorId);

        if (authorOpt.isEmpty()) {
            return List.of();
        }
        AppUser author = authorOpt.get();

        List<Follow> follows = followRepository.findByFollowing(author);
        // Extract just the follower IDs
        return follows.stream()
                .map(follow -> follow.getFollower().getId())
                .collect(Collectors.toList());
    }
}
