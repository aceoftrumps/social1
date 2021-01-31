package com.example.otus.hlarchitect.social1.services;

import com.example.otus.hlarchitect.social1.model.User;

import java.util.List;

public interface UserService {

    void saveAndAuthenticate(User user);

    List<String> validateUser(User user);
}
