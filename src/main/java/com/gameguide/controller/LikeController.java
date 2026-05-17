package com.gameguide.controller;

import com.gameguide.common.Result;
import com.gameguide.dto.LikeDTO;
import com.gameguide.service.LikeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public Result<?> toggle(Authentication auth, @RequestBody LikeDTO dto) {
        Long userId = (Long) auth.getPrincipal();
        return likeService.toggle(userId, dto);
    }

    @GetMapping("/status")
    public Result<?> status(Authentication auth,
                            @RequestParam Long targetId,
                            @RequestParam Integer targetType) {
        Long userId = (Long) auth.getPrincipal();
        LikeDTO dto = new LikeDTO();
        dto.setTargetId(targetId);
        dto.setTargetType(targetType);
        return likeService.status(userId, dto);
    }
}
