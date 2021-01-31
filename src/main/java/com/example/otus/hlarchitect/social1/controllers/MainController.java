package com.example.otus.hlarchitect.social1.controllers;


import com.example.otus.hlarchitect.social1.model.User;
import com.example.otus.hlarchitect.social1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class MainController {

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




}
