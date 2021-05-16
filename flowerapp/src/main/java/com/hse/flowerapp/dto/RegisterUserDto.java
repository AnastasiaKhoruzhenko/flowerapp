package com.hse.flowerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hse.flowerapp.domain.User;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserDto {
    String email;
    String name;
    String surname;
    String password;
    String role;
    String birthday;

    public User toUser() {
        User user = new User();

        user.setEmail(email);
        user.setName(name);
        user.setBirthday(birthday);
        user.setSurname(surname);
        user.setPassword(password);
        user.setAllowPush(true);
        user.setBonuses(0);
        user.setRole(role);

        return user;
    }
}
