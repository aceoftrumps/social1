package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.model.User;
import com.example.otus.hlarchitect.social1.repository.NewsRepository;
import com.example.otus.hlarchitect.social1.services.NewsService;
import com.example.otus.hlarchitect.social1.services.RabbitQueueService;
import com.example.otus.hlarchitect.social1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitQueueService rabbitQueueService;

    @Override
    public void add(String news) {
        User user =  userService.getAuthenticatedUser();
        newsRepository.save(news, user.getId());
        rabbitQueueService.addMessage(user.getName(), news);
    }
}
