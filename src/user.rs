use std::{io::{self, BufReader, BufRead}, collections::BTreeSet};
use std::fs::File;

/// User information structure.
#[derive(Debug, Clone)]
pub struct UserInfo {
    /// This value is unique 
    /// Example values: goldenlover1, petpal4ever, whiskerwatcher
    pub username: String,
    /// Example values: John Doe, Jane Doe.
    pub display_name: String,
    /// Example values: CA, NY, WV.
    pub state: String,
    /// Unique set of usernames
    pub friends: BTreeSet<String>
}

/// Read user information file into a vector of UserInfo structures.
pub fn read_user_info(file_path: &str) -> Result<Vec<UserInfo>, std::io::Error> {
    // Open the file and make a BufReader.
    let file = File::open(file_path)?;
    let reader = BufReader::new(file);
    let mut user_info_list = Vec::new();
    // For each line in the file parse the UserInfo.
    for line in reader.lines() {
        if let Ok(line_content) = line {
            let parts = line_content.split(';').collect::<Vec<&str>>();
            // Sanity check.
            if parts.len() == 4 {
                let username = parts[0].to_string();
                let display_name = parts[1].to_string();
                let state = parts[2].to_string();
                // Code stink but this parses all elements within the brackets.
                let friends = parts[3].trim_matches(|c| c == '[' || c == ']').trim()
                    .split(',')
                    .map(|friend| friend.trim().to_string())
                    .collect();
                // Save parsed user now.
                user_info_list.push(UserInfo {
                    username,
                    display_name,
                    state,
                    friends,
                });
            } else {
                eprintln!("Invalid line format: {}", line_content);
            }
        }
    }
    Ok(user_info_list)
}