package com.example.otus.hlarchitect.social1.controllers;


import com.example.otus.hlarchitect.social1.model.User;
import com.example.otus.hlarchitect.social1.services.UserService;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
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


    @GetMapping("/user")
    public Object getUser(@RequestParam(required = false) String fname,
                          @RequestParam(required = false) String lname){
        return userService.findUsers(fname, lname);
    }


    @GetMapping(value = "/user/random")
    public void createRandomUser()  {
        Faker faker = new Faker();
        Name name = faker.name();

        User user = new User();
        user.setName(name.username());
        user.setFirstname(name.firstName());
        user.setLastname(name.lastName());
        user.setPassword(RandomStringUtils.randomAlphabetic(8));
        user.setAge(RandomUtils.nextInt(18, 70));
        user.setSex(RandomUtils.nextInt()%2 == 0 ? "M" : "F");
        user.setInterests(RandomStringUtils.randomAlphabetic(10));
        user.setCity(faker.address().city());

        userService.save(user);

        System.out.println(user + " created");
    }

}
