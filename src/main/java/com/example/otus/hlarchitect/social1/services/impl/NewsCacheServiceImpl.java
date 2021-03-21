package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.repository.NewsRepository;
import com.example.otus.hlarchitect.social1.services.FollowerCacheService;
import com.example.otus.hlarchitect.social1.services.NewsCacheService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Log4j2
@Service
public class NewsCacheServiceImpl implements NewsCacheService {

    private final static int MAX_FEED_SIZE = 5;

    @Autowired
    private FollowerCacheService followerCacheService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private NewsRepository newsRepository;

    private Set<String> prefilledUsers = new HashSet<>();


    @Override
    public void putNewsItemToAllFollowers(String newsPoster, String newsMessage) {
        log.info("put news item '{}' from {} into cache", newsMessage, newsPoster);
        final List<String> followers = followerCacheService.getFollowers(newsPoster);

        followers.forEach(userName ->
                addNews(userName, buildNewsMessage(newsPoster, newsMessage)));
    }

    private String buildNewsMessage(String newsPoster, String newsMessage) {
        return newsPoster + ": " + newsMessage;
    }

    @Override
    public List<String> getNews(String userName){
        if (!prefilledUsers.contains(userName)) {
            fillCache(userName);
            prefilledUsers.add(userName);
        }
        return redisTemplate.opsForList().range(userName, 0, MAX_FEED_SIZE - 1);
    }

    private void fillCache(String userName) {
        final List<String> friendsNews = newsRepository.findFriendsNews(userName);
        redisTemplate.opsForList().leftPushAll(userName, friendsNews);
    }

    private void addNews(String userName, String newsItem){
        if (isPrefilledFromDB(userName)) {
            redisTemplate.opsForList().leftPush(userName, newsItem);

            //trim cache list if size > MAX_SIZE
            if (redisTemplate.opsForList().size(userName) > MAX_FEED_SIZE) {
                redisTemplate.opsForList().trim(userName, 0, MAX_FEED_SIZE - 1);
            }
        }
    }

    private boolean isPrefilledFromDB(String userName) {
        if (prefilledUsers.contains(userName)){
            return true;
        }
        if (redisTemplate.opsForList().size(userName) > 0){
            prefilledUsers.add(userName);
            return true;
        }
        return false;
    }
}
