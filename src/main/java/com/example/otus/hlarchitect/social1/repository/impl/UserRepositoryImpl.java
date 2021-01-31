package com.example.otus.hlarchitect.social1.repository.impl;

import com.example.otus.hlarchitect.social1.model.User;
import com.example.otus.hlarchitect.social1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public User findByUsername(String username) {

        final List<User> userList = jdbcTemplate.query("select id, name, firstname, lastname, password, age, sex, interests, city from users where name = ?",
                new BeanPropertyRowMapper<>(User.class),
                username);
        return userList.isEmpty() ? null : userList.get(0);
    }


    @Override
    public void save(User user) {
        jdbcTemplate.update("insert into users(name, password, firstname, lastname, age, sex, interests, city) values(?,?,?,?,?,?,?,?)",
                user.getName(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getAge(),
                user.getSex(),
                user.getInterests(),
                user.getCity());
    }
}
