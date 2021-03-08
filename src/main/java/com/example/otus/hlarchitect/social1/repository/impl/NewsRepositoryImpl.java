package com.example.otus.hlarchitect.social1.repository.impl;

import com.example.otus.hlarchitect.social1.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class NewsRepositoryImpl implements NewsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = false)
    public void save(String news, Integer userId) {
        jdbcTemplate.update("insert into news(nValue, userId) values(?, ?)",
                news,
                userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findMyNews(Integer userId) {
        return jdbcTemplate.queryForList("select nValue from News where userId = ? order by id desc limit 5",
                String.class, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findFriendsNews(Integer userId) {
        return jdbcTemplate.queryForList("select concat(u2.name, ': ', n.nValue) " +
                "from users u Join follow_map f ON u.id = f.userId " +
                "JOIN news n ON f.friendId = n.userId  " +
                "JOIN users u2 ON f.friendId = u2.id  " +
                "WHERE u.id = ? " +
                "order by n.id desc limit 5",
                String.class, userId);
    }
}
