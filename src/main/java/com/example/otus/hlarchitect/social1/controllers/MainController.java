package com.example.otus.hlarchitect.social1.controllers;

import com.example.otus.hlarchitect.social1.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public Object indexPage(){
        final Integer posts = postRepository.getPosts();
        return "Hello World " + posts;
    }
}
