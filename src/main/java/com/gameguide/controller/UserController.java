package com.gameguide.controller;

import com.gameguide.common.Result;
import com.gameguide.dto.LoginDTO;
import com.gameguide.dto.PasswordDTO;
import com.gameguide.dto.RegisterDTO;
import com.gameguide.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO dto) {
        return userService.login(dto);
    }

    @GetMapping("/info")
    public Result<?> info(Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return userService.info(userId);
    }

    @PutMapping("/password")
    public Result<?> password(Authentication auth, @RequestBody PasswordDTO dto) {
        Long userId = (Long) auth.getPrincipal();
        return userService.changePassword(userId, dto);
    }
}
