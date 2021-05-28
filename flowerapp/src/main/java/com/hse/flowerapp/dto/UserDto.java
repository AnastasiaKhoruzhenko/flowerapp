package com.hse.flowerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hse.flowerapp.domain.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long userId;
    private Long favouriteId;
    private String name;
    private String surname;
    private String email;
    private String birthday;
    private Integer bonuses;
    private Boolean allowPush;
    private String phone;
    private Integer workShop;
    private String role;

    public static UserDto toUserDto(User user) {
        log.info(user.getName());
        log.info(user.getPassword());
        log.info(user.getEmail());
        log.info(user.getRole());
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

        if(user.getWorkShop() != null)
            userDto.setWorkShop(Integer.valueOf(user.getWorkShop().getId().toString()));
        else
            userDto.setWorkShop(null);

        userDto.setUserId(user.getId());

        userDto.setRole(user.getRole());

        userDto.setFavouriteId(user.getFavouriteId());

        return userDto;
    }
}
