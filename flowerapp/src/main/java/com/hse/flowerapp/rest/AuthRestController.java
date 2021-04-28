package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Role;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.*;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping(value = "/api/auth/")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@Validated AuthRequestDto requestDto) {
        try {
            log.info(requestDto.getUsername()+"    "+requestDto.getPassword());
            String username = requestDto.getUsername();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByEmail(username);

            if (user == null)
                return ResponseEntity.ok(new ErrorDto(true, "login", "Такой пользователь не найден"));

            List<Role> roleList = user.getRoleList();
            log.info("before generating token " + username);
            String token = jwtTokenProvider.createToken(username, roleList);

            log.info(username + "    " + token);
            LoginResponseDto response = new LoginResponseDto();
            response.setUsername(username);
            response.setToken(token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new ErrorDto(true, "login", e.getMessage()));
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@Validated RegisterUserDto requestDto) {
        try {
            User user = requestDto.toUser();
            System.out.println(user.toString());
            System.out.println(requestDto.getRole());
            User result = userService.register(user, requestDto.getRole());

            if (result == null)
            {
                ErrorDto errorDto = new ErrorDto(true, "register", "Пользователь с такими данными уже существует");
                return ResponseEntity.ok(errorDto);
            }

            log.info(requestDto.toString());
            log.info(result.toString());
            return ResponseEntity.ok(UserDto.toUserDto(result));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new ErrorDto(true, "register successfull but error", e.getMessage()));
        }
    }
}
