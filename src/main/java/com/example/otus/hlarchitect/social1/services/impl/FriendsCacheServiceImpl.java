package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.services.FriendsCacheService;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class FriendsCacheServiceImpl implements FriendsCacheService {


    @Setter
    private MultiValueMap<String, String> friends;

    @Override
    public List<String> getFriends(String newsPoster) {
        return this.friends.get(newsPoster);
    }

    @Override
    public Set<String> getAllUsers() {
        return this.friends.keySet();
    }

    @Override
    public void removeFriend(String userName, String friendName){
        this.friends.remove(userName, friendName);
    }

    @Override
    public void addFriend(String userName, String friendName){
        this.friends.add(userName, friendName);
    }

    @Override
    public void addNewUser(String userName){
        this.friends.put(userName, new ArrayList<>());
    }
}
