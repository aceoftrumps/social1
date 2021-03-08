package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.model.Profile;
import com.example.otus.hlarchitect.social1.model.User;
import com.example.otus.hlarchitect.social1.repository.NewsRepository;
import com.example.otus.hlarchitect.social1.repository.UserRepository;
import com.example.otus.hlarchitect.social1.services.ProfileService;
import com.example.otus.hlarchitect.social1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public Profile getProfile(String name) {
        User user =  userService.getAuthenticatedUser();
        boolean isOwnProfile = name == null || user.getName().equals(name);
        boolean isFriendOfOwnProfile = false;

        if (!isOwnProfile) {
            final List<User> ownProfileFriends = userRepository.findUserFriendsByUsername(user.getName());
            isFriendOfOwnProfile = ownProfileFriends.stream().anyMatch(u -> u.getName().equals(name));
            user = userRepository.findByUsername(name);
        }

        return getProfile(user, isOwnProfile, isFriendOfOwnProfile);
    }

    private Profile getProfile(User user, boolean isOwnProfile, boolean isFriendOfOwnProfile){
        final List<User> friends = userRepository.findUserFriendsByUsername(user.getName());
        final List<User> nonFriends = userRepository.findUserNonFriendsByUsername(user.getName());

        final List<String> myNews = newsRepository.findMyNews(user.getId());
        final List<String> friendsNews = newsRepository.findFriendsNews(user.getId());

        return new Profile(user, isOwnProfile, isFriendOfOwnProfile, friends, nonFriends, myNews, friendsNews);
    }
}
