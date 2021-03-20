package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.services.FollowerCacheService;
import com.example.otus.hlarchitect.social1.services.NewsCacheService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.util.List;


@Log4j2
@Service
public class NewsCacheServiceImpl implements NewsCacheService {

    private final static int MAX_FEED_SIZE = 5;

    @Autowired
    private FollowerCacheService followerCacheService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public void putNewsItem(String newsPoster, String newsMessage) {
        log.info("put news item '{}' from {} into cahce", newsMessage, newsPoster);
        final List<String> followers = followerCacheService.getFollowers(newsPoster);

        followers.forEach(userName ->
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
