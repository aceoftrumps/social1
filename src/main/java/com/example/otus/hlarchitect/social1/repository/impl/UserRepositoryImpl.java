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
    public List<User> findUserFriendsByUsername(String username) {

        final String sql = "select u.* " +
                "from users u join follow_map f ON u.id = f.friendId " +
                "join users u2 ON f.userId = u2.id " +
                "where u2.name = ?";

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(User.class),
                username);
    }

    @Override
    public List<User> findUserNonFriendsByUsername(String username) {

        final String sql = "select u.* " +
                "from users u " +
                "WHERE NOT EXISTS (" +
                "SELECT * FROM follow_map f JOIN users u2 ON f.userId = u2.id " +
                "                    WHERE f.friendId = u.id AND u2.name = ?) " +
                " AND u.name != ?";

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(User.class),
                username, username);
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

    @Override
    public void follow(Integer id, String name) {
        String sql = "Insert into follow_map (userId, friendId) " +
                "SELECT ?, id " +
                "from users " +
                "WHERE name = ?";
        jdbcTemplate.update(sql, id, name);
    }

    @Override
    public void unfollow(Integer id, String name) {
        String sql = "delete f " +
                "from follow_map f " +
                "join users u ON u.id = f.friendId " +
                "WHERE u.name = ? AND f.userid = ?";
        jdbcTemplate.update(sql, name, id);
    }
}
