# Function to read user information from file
def read_user_info(file_path):
    user_info = {}
    with open(file_path, 'r') as file:
        for line in file:
            username, display_name, state, friends_list = line.strip().split(';')
            friends = friends_list.strip('[]').split(',') if friends_list.strip() else []
            user_info[username] = {'display_name': display_name, 'state': state, 'friends': friends}
    return user_info

# Function to read post information from file
def read_post_info(file_path):
    post_info = []
    with open(file_path, 'r') as file:
        for line in file:
            post_id, user_id, visibility = line.strip().split(';')
            post_info.append({'post_id': post_id, 'user_id': user_id, 'visibility': visibility})
    return post_info

# Function to check visibility of a post for a user
# Function to check visibility of a post for a user
def check_visibility(user_info, post_info, post_id, username):
    for post in post_info:
        # Check if the post ID matches and the user is authorized to view the post
        if post['post_id'] == post_id:
            if post['visibility'] == 'public':
                return True
            elif post['visibility'] == 'private' and post['user_id'] == username:
                return True
            elif post['visibility'] == 'friend' and (post['user_id'] == username or username in user_info.get(post['user_id'], {}).get('friends', [])):
                return True
    return False


# Function to retrieve posts accessible to a user
# Function to retrieve posts accessible to a user
# Function to retrieve posts accessible to a user
def retrieve_posts(user_info, post_info, username):
    accessible_posts = []
    for post in post_info:
        # Include user's own posts, public posts, and friend-only posts if user is authorized or if user is a friend of the post creator
        if post['user_id'] == username or post['visibility'] == 'public':
            accessible_posts.append(post['post_id'])
        elif post['visibility'] == 'friend':
            if username in user_info.get(post['user_id'], {}).get('friends', []) or username == post['user_id']:
                accessible_posts.append(post['post_id'])
    return accessible_posts



# Function to search users by location
def search_users_by_location(user_info, state):
    users_in_state = [info['display_name'] for username, info in user_info.items() if info['state'] == state]
    return users_in_state if users_in_state else ["No user in the state"]

# Main function
def main():
    user_info_file = "user-info.txt"  
    post_info_file = "post-info.txt"
    user_info = {}
    post_info = []

    while True:
        print("\nMenu:")
        print("1. Load input data.")
        print("2. Check visibility.")
        print("3. Retrieve posts.")
        print("4. Search users by location.")
        print("5. Exit.")

        choice = input("Enter your choice: ")

        if choice == '1':
            # Load user and post information from files
            user_info = read_user_info(user_info_file)
            post_info = read_post_info(post_info_file)
            print("Data loaded successfully.")

        elif choice == '2':
            if not user_info or not post_info:
                print("Please load input data first.")
                continue
            # Check the visibility of a post for a user
            post_id = input("Enter the post ID: ")
            username = input("Enter the username: ")
            if check_visibility(user_info, post_info, post_id, username):
                print("Access Permitted")
            else:
                print("Access Denied")

        elif choice == '3':
            if not user_info or not post_info:
                print("Please load input data first.")
                continue
            # Retrieve the posts accessible to a user
            username = input("Enter the username: ")
            accessible_posts = retrieve_posts(user_info, post_info, username)
            print("Accessible posts:", accessible_posts)

        elif choice == '4':
            if not user_info:
                print("Please load input data first.")
                continue
            # Searching users by location
            state = input("Enter the state location: ")
            users_in_state = search_users_by_location(user_info, state)
            print("Users in state", state, ":", ', '.join(users_in_state))

        elif choice == '5':
            print("Exiting program.")
            break

        else:
            print("Invalid choice. Please enter a number between 1 and 5.")

if __name__ == "__main__":
    main()
