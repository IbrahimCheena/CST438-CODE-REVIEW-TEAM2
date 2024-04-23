use std::{io::{self, BufReader, BufRead}, collections::BTreeSet};
use std::fs::File;

/// Visbility of a post.
#[derive(Debug, Clone, PartialEq)]
pub enum Visibility {
    /// Everyone can see it.
    Public,
    /// Just friends can see it.
    Friend
}
impl Visibility {
    // Make enum from string.
    pub fn from_str(s: &str) -> Option<Self> {
        match s.trim().to_lowercase().as_str() {
            "public" => Some(Visibility::Public),
            "friend" => Some(Visibility::Friend),
            _ => None,
        }
    }
}
/// Post information structure
pub struct PostInfo {
    /// unique ID of the post
    pub postid: String,
    /// unique ID of the user 
    /// Example values: petpal4ever, goldenlover1, whiskerwatcher
    pub userid: String,
    /// Visibility of the post.
    pub visibility: Visibility 
}
/// Read the contents of a post info file.
pub fn read_post_info(file_path: &str) -> Result<Vec<PostInfo>, std::io::Error> {
    // Open file and buf reader.
    let file = File::open(file_path)?;
    let reader = BufReader::new(file);
    let mut post_info_list = Vec::new();
    // Parse each line now.
    for line in reader.lines() {
        if let Ok(line_content) = line {
            let parts = line_content.split(';').collect::<Vec<&str>>();
            // Sanity check.
            if parts.len() == 3 {
                post_info_list.push(PostInfo {
                    postid: parts[0].to_string(),
                    userid: parts[1].to_string(),
                    visibility: Visibility::from_str(parts[2]).expect("Invalid visibility!"),
                });
            } else {
                eprintln!("Invalid line format: {}", line_content);
            }
        }
    }
    Ok(post_info_list)
}