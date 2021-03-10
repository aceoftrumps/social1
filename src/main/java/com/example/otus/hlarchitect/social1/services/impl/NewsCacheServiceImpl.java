package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.services.FriendsCacheService;
import com.example.otus.hlarchitect.social1.services.NewsCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;


@Service
public class NewsCacheServiceImpl implements NewsCacheService {


    @Autowired
    FriendsCacheService friendsCacheService;

    private MultiValueMap<String, String> news = new LinkedMultiValueMap<>();


    @Override
    public void putNewsItem(String newsPoster, String newsMessage) {
        final List<String> friends = friendsCacheService.getFriends(newsPoster);

        friends.forEach(userName ->
                news.add(userName, newsPoster + ": " + newsMessage));
    }

    @Override
    public List<String> getNews(String userName){
        return this.news.get(userName);
    }


}
