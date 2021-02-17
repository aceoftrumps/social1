package com.example.otus.hlarchitect.social1;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

@SpringBootTest
public class NameGenarator {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void generateUsers(){
        final int usersCount = 1000000;
        final int batchSize = 500;
        final int loopSize = usersCount / batchSize;

        Faker faker = new Faker();
        Instant firstStart = Instant.now();
        Instant start = null;
        Instant end = null;


        for (int j = 0; j < loopSize; j++ ) {
            start = Instant.now();
            jdbcTemplate.batchUpdate("insert into users(name, password, firstname, lastname, age, sex, interests, city) values(?,?,?,?,?,?,?,?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            Name name = faker.name();

                            ps.setString(1, name.username());
                            ps.setString(2, RandomStringUtils.randomAlphabetic(8));
                            ps.setString(3, name.firstName());
                            ps.setString(4, name.lastName());
                            ps.setInt(5, RandomUtils.nextInt(18, 70));
                            ps.setString(6, i%2 == 0 ? "M" : "F");
                            ps.setString(7, RandomStringUtils.randomAlphabetic(10));
                            ps.setString(8, faker.address().city());
                        }

                        @Override
                        public int getBatchSize() {
                            return batchSize;
                        }
                    });

            end = Instant.now();
            System.out.println("1: created " + (j+1)*batchSize + " records (" +  Duration.between(start, end).toMillis()
                    + " ms, total=" + Duration.between(firstStart, end).toMillis()/1000 + "sec.)");

        }
    }

}
