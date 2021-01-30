package com.example.otus.hlarchitect.social1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


     @GetMapping
    public Object indexPage(){
         return "Hello World";
     }

}
