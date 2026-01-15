package com.mcon152.recipeshare;

import com.mcon152.recipeshare.domain.*;
import com.mcon152.recipeshare.events.RecipeCreatedEvent;
import com.mcon152.recipeshare.repository.NotificationRepository;
import com.mcon152.recipeshare.service.FollowService;
import com.mcon152.recipeshare.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class ObserverPatternIntegrationTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private FollowService followService;

    @InjectMocks
    private NotificationService notificationService;

    @Captor
    ArgumentCaptor<Notification> notificationCaptor;

    @Test
    void TestThatUserCreatesRecipeAndFollowersGetNotifications() {
        //follower follows author, author creates recipe
        Long authorId = 1L;
        Long followerId = 2L;
        RecipeCreatedEvent event = mock(RecipeCreatedEvent.class);
        when(event.getAuthorId()).thenReturn(authorId);
        when(event.getRecipeTitle()).thenReturn("Chocolate Souffle Recipe");
        when(followService.getFollowers(authorId)).thenReturn(List.of(followerId));

        //notification is sent
        notificationService.handleRecipeCreated(event);

        //asserts that follower follows author and that the follower received correct notification for author
        verify(followService).getFollowers(authorId);
        verify(notificationRepository).save(notificationCaptor.capture());
        Notification saved = notificationCaptor.getValue();
        assertEquals(followerId, saved.getUserId());
        assertTrue(saved.getMessage().contains("Chocolate Souffle Recipe"));
        verifyNoMoreInteractions(notificationRepository);
        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void TestThatNoNotificationsWereSentIfAuthorHasNoUsers() {
            Long authorId = 1L;

            RecipeCreatedEvent event = mock(RecipeCreatedEvent.class);

            when(event.getAuthorId()).thenReturn(authorId);
            when(event.getRecipeTitle()).thenReturn("Chocolate Souffle Recipe");

            // Simulates that the user has unfollowed
            when(followService.getFollowers(authorId)).thenReturn(Collections.emptyList());

            notificationService.handleRecipeCreated(event);

            // Assert: no notification is saved
            verify(notificationRepository, never()).save(any());

            // Assert: followers were checked
            verify(followService).getFollowers(authorId);
    }
}
