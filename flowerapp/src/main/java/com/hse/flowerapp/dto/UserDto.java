package com.hse.flowerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hse.flowerapp.domain.User;
import lombok.Data;

import java.time.LocalDate;

// todo: Добавть поля необходимые для отображения

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private String birthday;
    private Integer bonuses;
    private Boolean allowPush;
    private String phone;

    public User toUser() {
        User user = new User();

        if(name != null)
            user.setName(name);
        else
            user.setName(null);

        if(surname != null)
            user.setSurname(surname);
        else
            user.setSurname(null);

        if(email != null)
            user.setEmail(email);

        if(birthday != null)
            user.setBirthday(birthday);
        else
            user.setBirthday(null);

        if(bonuses != null)
            user.setBonuses(bonuses);
        else
            user.setBonuses(null);

        if(allowPush != null)
            user.setAllowPush(allowPush);
        else
            user.setAllowPush(null);

        if(phone != null)
            user.setPhone(phone);
        else
            user.setPhone(null);

        return user;
    }

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();

        if(user.getName() != null)
            userDto.setName(user.getName());
        else
            userDto.setName(null);

        if(user.getSurname() != null)
            userDto.setSurname(user.getSurname());
        else
            userDto.setSurname(null);

        userDto.setEmail(user.getEmail());

        if(user.getBirthday() != null)
            userDto.setBirthday(user.getBirthday());
        else
            userDto.setBirthday(null);

        if(user.getBonuses() != null)
            userDto.setBonuses(user.getBonuses());
        else
            userDto.setBonuses(0);

        userDto.setAllowPush(user.getAllowPush());

        if(user.getPhone() != null)
            userDto.setPhone(user.getPhone());
        else
            userDto.setPhone(null);

        return userDto;
    }
}
