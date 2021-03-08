package com.example.otus.hlarchitect.social1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Profile {

    private User user;
    private boolean isOwnProfile;
    private boolean isFriendOfOwnProfile;
    private List<User> friends;
    private List<User> nonFriends;
    private List<String> myNews;
    private List<String> friendsNews;
}
