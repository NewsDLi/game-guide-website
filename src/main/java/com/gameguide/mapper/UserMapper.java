package com.gameguide.mapper;

import com.gameguide.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectByUsername(String username);
    User selectById(Long id);
    User selectByEmail(String email);
    void insert(User user);
    void updatePassword(@Param("id") Long id, @Param("password") String password);
}
