package com.gameguide.controller;

import com.gameguide.common.Result;
import com.gameguide.dto.GuideDTO;
import com.gameguide.entity.User;
import com.gameguide.mapper.UserMapper;
import com.gameguide.service.GuideService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guide")
public class GuideController {

    private final GuideService guideService;
    private final UserMapper userMapper;

    public GuideController(GuideService guideService, UserMapper userMapper) {
        this.guideService = guideService;
        this.userMapper = userMapper;
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) Long gameId,
                          @RequestParam(required = false) String tag,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(defaultValue = "new") String orderBy,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size) {
        return guideService.list(gameId, tag, keyword, orderBy, page, size);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id, Authentication auth) {
        Long userId = auth != null ? (Long) auth.getPrincipal() : null;
        return guideService.detail(id, userId);
    }

    @GetMapping("/my")
    public Result<?> myGuides(Authentication auth,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Long userId = (Long) auth.getPrincipal();
        return guideService.myGuides(userId, page, size);
    }

    @PostMapping
    public Result<?> create(Authentication auth, @RequestBody GuideDTO dto) {
        Long userId = (Long) auth.getPrincipal();
        return guideService.create(userId, dto);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id,
                            Authentication auth,
                            @RequestBody GuideDTO dto) {
        Long userId = (Long) auth.getPrincipal();
        User user = userMapper.selectById(userId);
        return guideService.update(id, userId, user != null ? user.getRole() : 0, dto);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        User user = userMapper.selectById(userId);
        return guideService.delete(id, userId, user != null ? user.getRole() : 0);
    }
}
