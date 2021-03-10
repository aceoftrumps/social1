package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.configs.UserPrincipal;
import com.example.otus.hlarchitect.social1.model.User;
import com.example.otus.hlarchitect.social1.repository.UserRepository;
import com.example.otus.hlarchitect.social1.services.FriendsCacheService;
import com.example.otus.hlarchitect.social1.services.RabbitQueueService;
import com.example.otus.hlarchitect.social1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private RabbitQueueService rabbitQueueService;
    @Autowired
    private FriendsCacheService friendsCacheService;

    @Override
    public void saveAndAuthenticate(User user){
        String rawPassword = user.getPassword();
        save(user);

        authenticateUser(user.getName(), rawPassword);
    }

    @Override
    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        rabbitQueueService.addNewQueue(user.getName());
    }

    @Override
    public List<String> validateUser(User user) {
        List<String> errors = new ArrayList<>();

        final User byUsername = userRepository.findByUsername(user.getName());
        if (byUsername != null){
            errors.add("User with such nickname already exists");
        }
        else {

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            final Set<ConstraintViolation<User>> validationErrors = validator.validate(user);
            errors = printErrors(validationErrors);
        }
        return errors;
    }

    @Override
    public User getAuthenticatedUser() {
        SecurityContext sc = SecurityContextHolder.getContext();
        final Authentication authentication = sc.getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

        return userPrincipal.getDbUser();
    }

    @Override
    public void follow(String name) {
        final User authenticatedUser = getAuthenticatedUser();
        userRepository.follow(authenticatedUser.getId(), name);
        friendsCacheService.addFriend(authenticatedUser.getName(), name);
    }

    @Override
    public void unfollow(String name) {
        final User authenticatedUser = getAuthenticatedUser();
        userRepository.unfollow(authenticatedUser.getId(), name);
        friendsCacheService.removeFriend(authenticatedUser.getName(), name);
    }

    @Override
    public List<User> findUsers(String fname, String lname) {
        return userRepository.findByFNameAndLName(fname, lname);
    }


    private List<String> printErrors(Set<ConstraintViolation<User>> validationErrors) {
        return validationErrors.stream().map(ve -> ve.getMessage()).collect(Collectors.toList());
    }


    private void authenticateUser(String userName, String rawPassword) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(userName, rawPassword);
        Authentication auth = authManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }
}
