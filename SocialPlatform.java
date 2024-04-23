import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Define a class to represent a user
class User {
    private String username;
    private String displayName;
    private String state;
    private List<String> friends;

    // Constructor to initialize user data
    public User(String username, String displayName, String state, List<String> friends) {
        this.username = username;
        this.displayName = displayName;
        this.state = state;
        this.friends = friends;
    }

    // Getter methods for user attributes
    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getState() {
        return state;
    }

    public List<String> getFriends() {
        return friends;
    }
}

// Define a class to represent a post
class Post {
    private String postId;
    private String userId;
    private String visibility;

    // Constructor to initialize post data
    public Post(String postId, String userId, String visibility) {
        this.postId = postId;
        this.userId = userId;
        this.visibility = visibility;
    }

    // Getter methods for post attributes
    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getVisibility() {
        return visibility;
    }
}

// Main class representing the social platform
public class SocialPlatform {
    private Map<String, User> users; // Map to store users by their usernames
    private List<Post> posts; // List to store all posts

    // Constructor to initialize the social platform
    public SocialPlatform() {
        users = new HashMap<>();
        posts = new ArrayList<>();
    }

    // Method to load user data from a file
    public void loadUserData() throws IOException {
        String fileName = "user-info.txt";
        String filePath = "C:\\Users\\ibrah\\CST 438 Code Review\\" + fileName;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            String username = parts[0];
            String displayName = parts[1];
            String state = parts[2];
            List<String> friends = parseFriends(parts[3]);
            users.put(username, new User(username, displayName, state, friends));
        }
        reader.close();
    }

    // Method to load post data from a file
    public void loadPostData() throws IOException {
        String fileName = "post-info.txt";
        String filePath = "C:\\Users\\ibrah\\CST 438 Code Review\\" + fileName;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            String postId = parts[0];
            String userId = parts[1];
            String visibility = parts[2];
            posts.add(new Post(postId, userId, visibility));
        }
        reader.close();
    }

    // Method to check if a user has access to view a post based on its visibility
    public void checkVisibility(String postId, String username) {
        boolean accessGranted = false;
        for (Post post : posts) {
            if (post.getPostId().equals(postId)) {
                if (post.getVisibility().equals("public")) {
                    accessGranted = true;
                    break;
                } else if (post.getVisibility().equals("friend")) {
                    User user = users.get(username);
                    if (user != null && user.getFriends().contains(post.getUserId())) {
                        accessGranted = true;
                        break;
                    }
                }
            }
        }
        if (accessGranted) {
            System.out.println("Access Permitted");
        } else {
            System.out.println("Access Denied");
        }
    }

    // Method to retrieve posts accessible to a user
    public List<String> retrievePosts(String username) {
        List<String> accessiblePosts = new ArrayList<>();
        for (Post post : posts) {
            if (!post.getUserId().equals(username)) {
                if (post.getVisibility().equals("public")) {
                    accessiblePosts.add(post.getPostId());
                } else if (post.getVisibility().equals("friend")) {
                    User user = users.get(username);
                    if (user != null && user.getFriends().contains(post.getUserId())) {
                        accessiblePosts.add(post.getPostId());
                    }
                }
            }
        }
        return accessiblePosts;
    }

    // Method to search for users by location
    public List<String> searchUsersByLocation(String state) {
        List<String> usersInState = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getState().equals(state)) {
                usersInState.add(user.getDisplayName());
            }
        }
        return usersInState;
    }

    // Utility method to parse the list of friends from a string
    private List<String> parseFriends(String friendsList) {
        List<String> friends = new ArrayList<>();
        if (!friendsList.equals("[]")) {
            String[] parts = friendsList.substring(1, friendsList.length() - 1).split(", ");
            for (String friend : parts) {
                friends.add(friend);
            }
        }
        return friends;
    }

    // Main method to run the social platform
    public static void main(String[] args) throws IOException {
        SocialPlatform socialPlatform = new SocialPlatform();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. Load input data");
            System.out.println("2. Check visibility");
            System.out.println("3. Retrieve posts");
            System.out.println("4. Search users by location");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    socialPlatform.loadUserData();
                    socialPlatform.loadPostData();
                    System.out.println("Data loaded successfully.");
                    break;
                case 2:
                    System.out.print("Enter post ID: ");
                    String postId = scanner.nextLine();
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    socialPlatform.checkVisibility(postId, username);
                    break;
                case 3:
                    System.out.print("Enter username: ");
                    String user = scanner.nextLine();
                    List<String> userPosts = socialPlatform.retrievePosts(user);
                    System.out.println("Accessible posts:");
                    for (String post : userPosts) {
                        System.out.println(post);
                    }
                    break;
                case 4:
                    System.out.print("Enter state: ");
                    String state = scanner.nextLine();
                    List<String> usersInState = socialPlatform.searchUsersByLocation(state);
                    System.out.println("Users in " + state + ":");
                    for (String userInState : usersInState) {
                        System.out.println(userInState);
                    }
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        } while (choice != 5);
        scanner.close();
    }
}
