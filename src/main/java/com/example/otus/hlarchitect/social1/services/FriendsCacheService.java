package com.example.otus.hlarchitect.social1.services;

import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

public interface FriendsCacheService {

    Set<String> getAllUsers();

    void setFriends(MultiValueMap<String, String> allFriends);

    void removeFriend(String userName, String friendName);

    void addFriend(String userName, String friendName);

    List<String> getFriends(String newsPoster);

    void addNewUser(String userName);
}
