package com.gameguide.controller;

import com.gameguide.common.Result;
import com.gameguide.service.TagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/hot")
    public Result<?> hot(@RequestParam(defaultValue = "20") int limit) {
        return tagService.hot(limit);
    }

    @GetMapping("/search")
    public Result<?> search(@RequestParam String keyword,
                            @RequestParam(defaultValue = "10") int limit) {
        return tagService.search(keyword, limit);
    }
}
