package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.repository.UserRepository;
import com.example.otus.hlarchitect.social1.services.FriendsCacheService;
import com.example.otus.hlarchitect.social1.services.RabbitQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitializerServiceImpl {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendsCacheService friendsCacheService;
    @Autowired
    private RabbitQueueService rabbitQueueService;


    @EventListener
    private void onApplicationEvent(ContextRefreshedEvent event) {
        friendsCacheService.setFriends(userRepository.findAllFriends());

        friendsCacheService.getAllUsers().forEach(userName ->
                rabbitQueueService.addNewQueue(userName)
        );
    }
}
