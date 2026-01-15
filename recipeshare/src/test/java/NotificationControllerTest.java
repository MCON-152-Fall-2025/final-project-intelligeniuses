import com.mcon152.recipeshare.domain.Notification;
import com.mcon152.recipeshare.service.NotificationService;
import com.mcon152.recipeshare.web.NotificationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class NotificationControllerTest {
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private Notification notification1;
    private Notification notification2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        notification1 = new Notification(1L, "Try out this new recipe!", false);
        notification2 = new Notification(2L, "Must see!", true);
    }

    @Test
    void testGetNotifications() {
        //asserts that a list of read notifications was received and
        // verifies that the getReadNotificationsForUser was called only once
        when(notificationService.getNotificationsForUser(1L))
                .thenReturn(List.of(notification1, notification2));

        List<Notification> result = notificationController.getNotifications(1L);

        assertEquals(2, result.size());
        verify(notificationService, times(1)).getNotificationsForUser(1L);
    }

    @Test
    void testGetUnreadNotifications() {
        //asserts that a list of unread notifications was received and
        // verifies that the getUnreadNotificationsForUser was called only once
        when(notificationService.getUnreadNotificationsForUser(1L))
                .thenReturn(List.of(notification1));

        List<Notification> result = notificationController.getUnreadNotifications(1L);

        assertEquals(1, result.size());
        assertFalse(result.get(0).isRead());
        verify(notificationService, times(1)).getUnreadNotificationsForUser(1L);
    }

    @Test
    void testReadNotifications() {
        //asserts that notifications are marked as read and verifies that markAsRead was called once
        when(notificationService.markAsRead(1L))
                .thenReturn(notification1);
        Notification result = notificationController.readNotification(1L);

        assertEquals(1L, result.getUserId());
        verify(notificationService, times(1)).markAsRead(1L);
    }

    @Test
    void testInvalidUserId(){
        //asserts that Runtime exception is thrown when given user id is not found
        when(notificationService.getNotificationsForUser(1L)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> notificationController.getNotifications(1L));
    }

    @Test
    void testInvalidNotificationId(){
        //asserts that Runtime exception is thrown when given notification id is not found
        when(notificationService.markAsRead(1L)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> notificationController.readNotification(1L));
    }

    @Test
    void testGetNotificationsWhenUserHasNoNotifications() {
        //asserts that list of notifications is empty
        when(notificationService.getNotificationsForUser(1L)).thenReturn(List.of());
        List<Notification> result = notificationController.getNotifications(1L);
        assertEquals(0, result.size());
        verify(notificationService, times(1)).getNotificationsForUser(1L);
    }


}
