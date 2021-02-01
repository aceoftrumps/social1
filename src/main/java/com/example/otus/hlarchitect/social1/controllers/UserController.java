package com.example.otus.hlarchitect.social1.controllers;


import com.example.otus.hlarchitect.social1.model.Profile;
import com.example.otus.hlarchitect.social1.model.User;
import com.example.otus.hlarchitect.social1.services.ProfileService;
import com.example.otus.hlarchitect.social1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/signup_processed")
    public ModelAndView signUp(@ModelAttribute("user") User user, ModelMap model)  {
        final List<String> validationErrors = userService.validateUser(user);

        if (!validationErrors.isEmpty()){
            model.addAttribute("error", true);
            model.addAttribute("errorMessages", validationErrors);

            return new ModelAndView("redirect:/signup", model);
        }

        userService.saveAndAuthenticate(user);

        return new ModelAndView("redirect:/profile");
    }


    @PostMapping("/follow")
    public void follow(@RequestBody MultiValueMap<String, String> formData){
        final String name = formData.getFirst("name");
        userService.follow(name);
    }


    @PostMapping("/unfollow")
    public void unfollow(@RequestBody MultiValueMap<String, String> formData){
        final String name = formData.getFirst("name");
        userService.unfollow(name);
    }



}
