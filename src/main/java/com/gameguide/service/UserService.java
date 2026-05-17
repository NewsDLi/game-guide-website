package com.gameguide.service;

import com.gameguide.common.Result;
import com.gameguide.dto.LoginDTO;
import com.gameguide.dto.PasswordDTO;
import com.gameguide.dto.RegisterDTO;
import com.gameguide.entity.User;
import com.gameguide.mapper.UserMapper;
import com.gameguide.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Result<?> register(RegisterDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().length() < 3 || dto.getUsername().length() > 32) {
            return Result.fail(400, "用户名需3-32位");
        }
        if (dto.getPassword() == null || dto.getPassword().length() < 6) {
            return Result.fail(400, "密码需6位以上");
        }
        if (userMapper.selectByUsername(dto.getUsername()) != null) {
            return Result.fail(400, "用户名已被注册");
        }
        if (dto.getEmail() != null && userMapper.selectByEmail(dto.getEmail()) != null) {
            return Result.fail(400, "邮箱已被注册");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : dto.getUsername());
        user.setEmail(dto.getEmail());
        userMapper.insert(user);

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickname());
        data.put("role", 0);
        return Result.ok("注册成功", data);
    }

    public Result<?> login(LoginDTO dto) {
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return Result.fail(400, "用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            return Result.fail(403, "账号已被禁用");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("avatar", user.getAvatar());
        userInfo.put("role", user.getRole());

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("expireIn", jwtUtil.getExpireSeconds());
        data.put("userInfo", userInfo);
        return Result.ok("登录成功", data);
    }

    public Result<?> info(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.fail(404, "用户不存在");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickname());
        data.put("avatar", user.getAvatar());
        data.put("email", user.getEmail());
        data.put("role", user.getRole());
        data.put("createdAt", user.getCreatedAt());
        return Result.ok(data);
    }

    public Result<?> changePassword(Long userId, PasswordDTO dto) {
        User user = userMapper.selectById(userId);
        if (user == null || !passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            return Result.fail(400, "当前密码错误");
        }
        if (dto.getNewPassword() == null || dto.getNewPassword().length() < 6) {
            return Result.fail(400, "新密码需6位以上");
        }
        userMapper.updatePassword(userId, passwordEncoder.encode(dto.getNewPassword()));
        return Result.ok("密码修改成功", null);
    }
}
