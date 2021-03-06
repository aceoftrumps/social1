package com.example.otus.hlarchitect.social1.services;

import com.example.otus.hlarchitect.social1.model.User;

import java.util.List;

public interface UserService {

    void saveAndAuthenticate(User user);

    void save(User user);

    List<String> validateUser(User user);

    User getAuthenticatedUser();

    void follow(String name);

    void unfollow(String name);

    List<User> findUsers(String fname, String lname);
}
