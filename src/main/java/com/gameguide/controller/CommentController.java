package com.gameguide.controller;

import com.gameguide.common.Result;
import com.gameguide.dto.CommentDTO;
import com.gameguide.entity.User;
import com.gameguide.mapper.UserMapper;
import com.gameguide.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserMapper userMapper;

    public CommentController(CommentService commentService, UserMapper userMapper) {
        this.commentService = commentService;
        this.userMapper = userMapper;
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam Long guideId,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "20") int size) {
        return commentService.list(guideId, page, size);
    }

    @PostMapping
    public Result<?> create(Authentication auth, @RequestBody CommentDTO dto) {
        Long userId = (Long) auth.getPrincipal();
        return commentService.create(userId, dto);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        User user = userMapper.selectById(userId);
        return commentService.delete(id, userId, user != null ? user.getRole() : 0);
    }
}
