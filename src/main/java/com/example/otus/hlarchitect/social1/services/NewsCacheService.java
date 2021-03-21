package com.example.otus.hlarchitect.social1.services;


import java.util.List;

public interface NewsCacheService {

    void putNewsItemToAllFollowers(String newsPoster, String newsMessage);

    List<String> getNews(String userName);

}
