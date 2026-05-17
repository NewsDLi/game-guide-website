package com.gameguide.service;

import com.gameguide.common.Result;
import com.gameguide.dto.CommentDTO;
import com.gameguide.dto.PageResult;
import com.gameguide.entity.Comment;
import com.gameguide.mapper.CommentMapper;
import com.gameguide.mapper.GuideMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final GuideMapper guideMapper;

    public CommentService(CommentMapper commentMapper, GuideMapper guideMapper) {
        this.commentMapper = commentMapper;
        this.guideMapper = guideMapper;
    }

    public Result<?> list(Long guideId, int page, int size) {
        int offset = (page - 1) * size;
        List<Comment> comments = commentMapper.selectByGuideId(guideId, offset, size);
        for (Comment comment : comments) {
            List<Comment> replies = commentMapper.selectReplies(comment.getId());
            comment.setReplies(replies);
        }
        long total = commentMapper.countByGuideId(guideId);
        Map<String, Object> data = PageResult.of(total, page, size, comments).toMap();
        return Result.ok(data);
    }

    @Transactional
    public Result<?> create(Long userId, CommentDTO dto) {
        if (!StringUtils.hasText(dto.getContent())) {
            return Result.fail(400, "评论内容不能为空");
        }
        if (dto.getContent().length() > 500) {
            return Result.fail(400, "评论最长500字");
        }

        Comment comment = new Comment();
        comment.setGuideId(dto.getGuideId());
        comment.setUserId(userId);
        comment.setContent(dto.getContent());
        comment.setParentId(dto.getParentId());
        comment.setReplyUserId(dto.getReplyUserId());
        commentMapper.insert(comment);

        guideMapper.updateCommentCount(dto.getGuideId(), 1);

        Comment saved = commentMapper.selectById(comment.getId());
        return Result.ok("评论成功", saved);
    }

    @Transactional
    public Result<?> delete(Long id, Long userId, Integer userRole) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            return Result.fail(404, "评论不存在");
        }
        if (!comment.getUserId().equals(userId) && userRole != 1) {
            return Result.fail(403, "无权限操作");
        }

        commentMapper.deleteById(id);
        guideMapper.updateCommentCount(comment.getGuideId(), -1);
        return Result.ok("删除成功", null);
    }
}
