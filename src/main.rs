use std::io;

use post::PostInfo;
use user::UserInfo;

pub mod post;
pub mod user;

const MENU: &str = r#"
Menu:
1. Load input data
2. Check visibility
3. Retrieve posts
4. Search users by location
5. Exit
"#;

fn main() {
    loop {
        let mut users = Vec::<UserInfo>::new();
        let mut posts = Vec::<PostInfo>::new();
        // Print display menu
        print!("{}", MENU);
        // Read in the option.
        let mut choice = String::new();
        io::stdin()
            .read_line(&mut choice)
            .expect("Failed to read line");
        // Handle each option and failueres.
        match choice.trim().parse() {
            Ok(1) => {
                let mut users_file_path = String::new();
                let mut posts_file_path = String::new();
                println!("Enter users file: ");
                io::stdin()
                    .read_line(&mut users_file_path)
                    .expect("Failed to read line");
                println!("Enter posts file: ");
                io::stdin()
                    .read_line(&mut posts_file_path)
                    .expect("Failed to read line");
                
                // Loads the contents of users and posts.
                users = user::read_user_info(&users_file_path)
                    .expect("Failed to parse users information!");
                posts =
                    post::read_post_info(&posts_file_path).expect("Failed to parse posts file!");
            }
            Ok(2) => {
                let mut input = String::new();
                print!("Enter post ID: ");
                io::stdin()
                    .read_line(&mut input)
                    .expect("Failed to read line");
                let post_id: u32 = input.trim().parse().unwrap();
                println!("Enter username:");
                input.clear();
                io::stdin()
                    .read_line(&mut input)
                    .expect("Failed to read line");
                let username = input.trim();
            }
            Ok(3) => {
                let mut input = String::new();
                println!("Enter username:");
                io::stdin()
                    .read_line(&mut input)
                    .expect("Failed to read line");
                let username = input.trim();
            }
            Ok(4) => {
                let mut input = String::new();
                println!("Enter location:");
                io::stdin()
                    .read_line(&mut input)
                    .expect("Failed to read line");
                let location = input.trim();
            }
            Ok(5) => {
                println!("Exiting program...");
                break;
            }
            _ => println!("Invalid choice. Please select a number between 1 and 5."),
        }
    }
}
