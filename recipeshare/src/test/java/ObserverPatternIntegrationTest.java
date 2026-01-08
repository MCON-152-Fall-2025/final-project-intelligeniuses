import com.mcon152.recipeshare.domain.AppUser;
import com.mcon152.recipeshare.domain.DessertRecipe;
import com.mcon152.recipeshare.domain.Follow;
import com.mcon152.recipeshare.repository.AppUserRepository;
import com.mcon152.recipeshare.repository.FollowRepository;
import com.mcon152.recipeshare.repository.RecipeRepository;
import com.mcon152.recipeshare.service.FollowService;
import com.mcon152.recipeshare.service.RecipeService;
import com.mcon152.recipeshare.service.RecipeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ObserverPatternIntegrationTest {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeServiceImpl recipeService;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    FollowService followService;
    @Autowired
    NotificationService notificationService;


    @Test
    void TestThatUserCreatesRecipeAndFollowersGetNotifications() {
        //Users are created
        AppUser A = new  AppUser();
        A.setUsername("John Smith");
        A.setPassword("123");
        AppUser B = new AppUser();
        B.setUsername("Bob Jay");
        B.setPassword("456");
        A = appUserRepository.save(A);
        B = appUserRepository.save(B);

        Follow f = new Follow();
        f.setFollower(A);
        f.setFollowing(B);
        followRepository.save(f);

        DessertRecipe dessert = new DessertRecipe(
                1L,
                "Chocolate Souffle",
                "Cake with chocolate filling",
                "Flour,Oil,Sugar,Water,Baking Soda,Cocoa powder",
                "Mix well and bake!",
                12,B);
        recipeService.addRecipe(dessert);

        List<Notification> notificationsForUserA = notificationService.getNotificationsForUser(A.getId());
        assertFalse(notificationsForUserA.isEmpty());
    }

}
