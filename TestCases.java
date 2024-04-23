import java.io.IOException;
import java.util.List;

public class TestCases {
    public static void main(String[] args) {
        SocialPlatform socialPlatform = new SocialPlatform();

        // Load sample user data
        try {
            socialPlatform.loadUserData();
        } catch (IOException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }

        // Load sample post data
        try {
            socialPlatform.loadPostData();
        } catch (IOException e) {
            System.out.println("Error loading post data: " + e.getMessage());
        }

        // Test Case 1: Checking visibility of a public post
        System.out.println("Test Case 1:");
        socialPlatform.checkVisibility("post3298", "goldenlover1"); // Access Denied

        // Test Case 2: Checking visibility of a friend's post where the user is a friend
        System.out.println("\nTest Case 2:");
        socialPlatform.checkVisibility("post2123", "goldenlover1"); // Access Denied

        // Test Case 3: Checking visibility of a friend's post where the user is not a friend
        System.out.println("\nTest Case 3:");
        socialPlatform.checkVisibility("post1112", "goldenlover1"); // Access Denied

        // Test Case 4: Retrieving posts for a user
        System.out.println("\nTest Case 4:");
        List<String> alicePosts = socialPlatform.retrievePosts("goldenlover1");
        System.out.println("Alice's Posts: " + alicePosts); // [post3298]

        // Test Case 5: Searching users by location
        System.out.println("\nTest Case 5:");
        List<String> usersInCalifornia = socialPlatform.searchUsersByLocation("CA");
        System.out.println("Users in California: " + usersInCalifornia); // []
    }
}
