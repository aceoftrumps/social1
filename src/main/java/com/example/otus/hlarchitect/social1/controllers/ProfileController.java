package com.example.otus.hlarchitect.social1.controllers;

import com.example.otus.hlarchitect.social1.model.Profile;
import com.example.otus.hlarchitect.social1.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profiledata")
    public Object getProfileData(@RequestParam(required = false) String name){
        Profile profile = profileService.getProfile(name);
        return profile;
    }
}
