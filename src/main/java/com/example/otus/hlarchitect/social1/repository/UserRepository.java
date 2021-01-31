package com.example.otus.hlarchitect.social1.repository;

import com.example.otus.hlarchitect.social1.model.User;

public interface UserRepository {
    User findByUsername(String username);

    void save(User user);
}
