package com.example.otus.hlarchitect.social1.repository;

import com.example.otus.hlarchitect.social1.model.User;

import java.util.List;

public interface UserRepository {
    User findByUsername(String username);

    List<User> findUserFriendsByUsername(String username);

    List<User> findUserNonFriendsByUsername(String username);

    void save(User user);

    void follow(Integer id, String name);

    void unfollow(Integer id, String name);

    List<User> findByFNameAndLName(String fname, String lname);
}
