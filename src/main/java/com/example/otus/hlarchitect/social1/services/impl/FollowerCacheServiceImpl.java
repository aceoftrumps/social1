package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.services.FollowerCacheService;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class FollowerCacheServiceImpl implements FollowerCacheService {


    @Setter
    private MultiValueMap<String, String> followers;

    @Override
    public List<String> getFollowers(String newsPoster) {
        return this.followers.get(newsPoster);
    }

    @Override
    public Set<String> getAllUsers() {
        return this.followers.keySet();
    }

    @Override
    public void removeFollower(String userName, String follower){
        this.followers.remove(userName, follower);
    }

    @Override
    public void addFollower(String userName, String follower){
        this.followers.add(userName, follower);
    }

    @Override
    public void addNewUser(String userName){
        this.followers.put(userName, new ArrayList<>());
    }
}
