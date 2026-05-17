package com.gameguide.controller;

import com.gameguide.common.Result;
import com.gameguide.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) String category,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "12") int size) {
        return gameService.list(category, page, size);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return gameService.detail(id);
    }
}
