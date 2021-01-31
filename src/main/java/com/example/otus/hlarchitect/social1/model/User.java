package com.example.otus.hlarchitect.social1.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Data
public class User {

    private Integer id;

    @Size(min = 3, max = 45, message
            = "nickname must be between 3 and 45 characters")
    private String name;

    @Size(min = 3, max = 45, message
            = "firstname must be between 3 and 45 characters")
    private String firstname;

    @Size(min = 3, max = 45, message
            = "lastname must be between 3 and 45 characters")
    private String lastname;

    @Size(min = 1, max = 10, message
            = "password must be between 1 and 10 characters")
    private String password;

    @Positive
    @Max(value = 99, message = "age must be less than 100")
    private Integer age;

    private String sex;

    @Size(min = 0, max = 200, message
            = "interests must be between 0 and 200 characters")
    private String interests;

    @Size(min = 0, max = 45, message
            = "city must be between 0 and 45 characters")
    private String city;

}
