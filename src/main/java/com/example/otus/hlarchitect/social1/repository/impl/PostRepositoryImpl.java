package com.example.otus.hlarchitect.social1.repository.impl;


import com.example.otus.hlarchitect.social1.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer getPosts() {
        return jdbcTemplate
                .queryForObject("select count(*) from test1", Integer.class);
    }
}
