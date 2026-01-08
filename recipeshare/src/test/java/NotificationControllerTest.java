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
        when(notificationService.getNotificationsForUser(1L))
                .thenReturn(List.of(notification1, notification2));

        List<Notification> result = notificationController.getNotifications(1L);

        assertEquals(2, result.size());
        verify(notificationService, times(1)).getNotificationsForUser(1L);
    }

    @Test
    void testGetUnreadNotifications() {
        when(notificationService.getUnreadNotificationsForUser(1L))
                .thenReturn(List.of(notification1));

        List<Notification> result = notificationController.getUnreadNotifications(1L);

        assertEquals(1, result.size());
        assertFalse(result.get(0).isRead());
        verify(notificationService, times(1)).getUnreadNotificationsForUser(1L);
    }

    @Test
    void testReadNotifications() {
        when(notificationService.markAsRead(1L))
                .thenReturn(notification1);
        Notification result = notificationController.readNotification(1L);

        assertEquals(1L, result.getUserId());
        verify(notificationService, times(1)).markAsRead(1L);
    }
}
