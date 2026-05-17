package com.gameguide.service;

import com.gameguide.common.Result;
import com.gameguide.dto.LikeDTO;
import com.gameguide.entity.Like;
import com.gameguide.mapper.CommentMapper;
import com.gameguide.mapper.GuideMapper;
import com.gameguide.mapper.LikeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class LikeService {

    private final LikeMapper likeMapper;
    private final GuideMapper guideMapper;
    private final CommentMapper commentMapper;

    public LikeService(LikeMapper likeMapper, GuideMapper guideMapper, CommentMapper commentMapper) {
        this.likeMapper = likeMapper;
        this.guideMapper = guideMapper;
        this.commentMapper = commentMapper;
    }

    @Transactional
    public Result<?> toggle(Long userId, LikeDTO dto) {
        Like existing = likeMapper.selectByUserAndTarget(userId, dto.getTargetId(), dto.getTargetType());
        boolean liked;
        if (existing != null) {
            likeMapper.deleteById(existing.getId());
            liked = false;
            // decrement like count on target
            if (dto.getTargetType() == 1) {
                guideMapper.updateLikeCount(dto.getTargetId(), -1);
            } else {
                commentMapper.updateLikeCount(dto.getTargetId(), -1);
            }
        } else {
            Like like = new Like();
            like.setUserId(userId);
            like.setTargetId(dto.getTargetId());
            like.setTargetType(dto.getTargetType());
            likeMapper.insert(like);
            liked = true;
            // increment like count on target
            if (dto.getTargetType() == 1) {
                guideMapper.updateLikeCount(dto.getTargetId(), 1);
            } else {
                commentMapper.updateLikeCount(dto.getTargetId(), 1);
            }
        }

        int likeCount = likeMapper.countByTarget(dto.getTargetId(), dto.getTargetType());
        Map<String, Object> data = new HashMap<>();
        data.put("liked", liked);
        data.put("likeCount", likeCount);
        return Result.ok(data);
    }

    public Result<?> status(Long userId, LikeDTO dto) {
        Like existing = likeMapper.selectByUserAndTarget(userId, dto.getTargetId(), dto.getTargetType());
        int likeCount = likeMapper.countByTarget(dto.getTargetId(), dto.getTargetType());
        Map<String, Object> data = new HashMap<>();
        data.put("liked", existing != null);
        data.put("likeCount", likeCount);
        return Result.ok(data);
    }
}
