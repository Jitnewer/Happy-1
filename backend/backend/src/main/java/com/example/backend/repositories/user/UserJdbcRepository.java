package com.example.backend.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcRepository {
    @Autowired
    private JdbcTemplate springJdbcTemplate;

    private static final String INSERT_QUERY =
            """
                INSERT INTO "user" (profilepic, firstname, lastname, mail, gender, age, companytype, tag, status, usertype, postalcode, password)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;




    public void insert() {
        springJdbcTemplate.update(
                INSERT_QUERY,
                "picture.png",
                "Rick",
                "Veerman",
                "rickveerman4@gmail.com",
                "male",
                21,
                "Catering",
                "ACTIVE",
                "ACTIVE",
                "ADMIN",
                "1132RR",
                "test"
        );
    }


}
