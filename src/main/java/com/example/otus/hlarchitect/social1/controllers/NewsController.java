package com.example.otus.hlarchitect.social1.controllers;

import com.example.otus.hlarchitect.social1.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/news")
    void addNews(@RequestBody MultiValueMap<String, String> formData){
        final String news = formData.getFirst("value");
        newsService.add(news);
    }
}
