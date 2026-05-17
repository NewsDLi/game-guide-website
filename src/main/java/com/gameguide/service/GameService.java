package com.gameguide.service;

import com.gameguide.common.Result;
import com.gameguide.dto.PageResult;
import com.gameguide.entity.Game;
import com.gameguide.mapper.GameMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GameService {

    private final GameMapper gameMapper;

    public GameService(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }

    public Result<?> list(String category, int page, int size) {
        int offset = (page - 1) * size;
        List<Game> list = gameMapper.selectList(category, offset, size);
        long total = gameMapper.countByCategory(category);
        Map<String, Object> data = PageResult.of(total, page, size, list).toMap();
        return Result.ok(data);
    }

    public Result<?> detail(Long id) {
        Game game = gameMapper.selectById(id);
        if (game == null) {
            return Result.fail(404, "游戏不存在");
        }
        return Result.ok(game);
    }

    public Game getGame(Long id) {
        return gameMapper.selectById(id);
    }
}
