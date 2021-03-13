package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.services.FriendsCacheService;
import com.example.otus.hlarchitect.social1.services.NewsCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class NewsCacheServiceImpl implements NewsCacheService {

    private final static int MAX_FEED_SIZE = 5;

    @Autowired
    private FriendsCacheService friendsCacheService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public void putNewsItem(String newsPoster, String newsMessage) {
        final List<String> friends = friendsCacheService.getFriends(newsPoster);

        friends.forEach(userName ->
                addNews(userName, buildNewsMessage(newsPoster, newsMessage)));
    }

    private String buildNewsMessage(String newsPoster, String newsMessage) {
        return newsPoster + ": " + newsMessage;
    }

    @Override
    public List<String> getNews(String userName){
        return redisTemplate.opsForList().range(userName, 0, MAX_FEED_SIZE - 1);
    }

    private void addNews(String userName, String newsItem){
        redisTemplate.opsForList().leftPush(userName, newsItem);

        //trim cache list if size > MAX_SIZE
        if (redisTemplate.opsForList().size(userName) > MAX_FEED_SIZE){
            redisTemplate.opsForList().trim(userName, 0, MAX_FEED_SIZE - 1);
        }
    }
}
