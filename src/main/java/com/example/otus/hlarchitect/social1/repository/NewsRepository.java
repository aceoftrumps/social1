package com.example.otus.hlarchitect.social1.repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NewsRepository {
    void save(String news, Integer userId);

    List<String> findMyNews(Integer userId);

    List<String> findFriendsNews(Integer userId);

    List<String> findFriendsNews(String userName);
}
