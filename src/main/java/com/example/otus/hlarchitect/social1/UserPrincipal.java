package com.example.otus.hlarchitect.social1;

import com.example.otus.hlarchitect.social1.model.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Getter
public class UserPrincipal extends org.springframework.security.core.userdetails.User {

    private User dbUser;

    public UserPrincipal(User dbUser){
        super(dbUser.getName(), dbUser.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("User")));
        this.dbUser = dbUser;
    }
}
