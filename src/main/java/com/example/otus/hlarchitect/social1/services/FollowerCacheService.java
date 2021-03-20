package com.example.otus.hlarchitect.social1.services;

import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

public interface FollowerCacheService {

    Set<String> getAllUsers();

    void setFollowers(MultiValueMap<String, String> allFriends);

    void removeFollower(String userName, String friendName);

    void addFollower(String userName, String friendName);

    List<String> getFollowers(String newsPoster);

    void addNewUser(String userName);
}
