package com.apis.controllers;

import com.apis.dtos.LogOutRequestDto;
import com.apis.dtos.LoginRequestDto;
import com.apis.dtos.SignUpRequestDto;
import com.apis.dtos.UserDto;
import com.apis.models.Token;
import com.apis.models.User;
import com.apis.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        User user = userService.save(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        return UserDto.from(user);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable Long id)
    {
        return null;
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogOutRequestDto logOutRequestDto){
        userService.logout(logOutRequestDto.getToken());
    }

    @GetMapping("/validateToken/{tokenValue}")
    public UserDto validateToken(@PathVariable String tokenValue){
        User user = userService.validateToken(tokenValue);
        return UserDto.from(user);
    }
}
