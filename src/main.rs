use std::{io, collections::BTreeSet};

/// User information structure.
struct UserInfo {
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

/// Visbility of a post.
enum Visibility {
    /// Everyone can see it.
    Public,
    /// Just friends can see it.
    Friend
}

/// Post information structure
struct PostInfo {
    /// unique ID of the post
    pub postid: String,
    /// unique ID of the user 
    /// Example values: petpal4ever, goldenlover1, whiskerwatcher
    pub userid: String,
    /// Visibility of the post.
    pub visibility: Visibility 
}

fn main() {
    println!("Hello, world!");
}
