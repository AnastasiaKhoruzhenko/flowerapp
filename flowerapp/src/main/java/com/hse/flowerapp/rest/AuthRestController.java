package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Role;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.AuthRequestDto;
import com.hse.flowerapp.dto.LoginResponseDto;
import com.hse.flowerapp.dto.RegisterUserDto;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity login(@RequestBody AuthRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByEmail(username);

            if (user == null)
                throw new UsernameNotFoundException("User with username: " + username + " not found");

            List<Role> roleList = user.getRoleList();
            String token = jwtTokenProvider.createToken(username, roleList);

            LoginResponseDto response = new LoginResponseDto();
            response.setUsername(username);
            response.setToken(token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterUserDto requestDto) {
        try {
            User user = requestDto.toUser();
            System.out.println(user.toString());
            System.out.println(requestDto.getRole());
            User result = userService.register(user, requestDto.getRole());

            if (result == null)
                throw new UsernameNotFoundException("Invalid login or password");

            return ResponseEntity.ok("User with username: " + user.getEmail() + " and id: " + user.getId()
                    + " was registered successfully");
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid data");
        }
    }
}
