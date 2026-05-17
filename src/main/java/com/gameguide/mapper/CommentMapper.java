package com.gameguide.mapper;

import com.gameguide.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectByGuideId(@Param("guideId") Long guideId,
                                   @Param("offset") int offset,
                                   @Param("size") int size);
    long countByGuideId(Long guideId);
    List<Comment> selectReplies(Long parentId);
    void insert(Comment comment);
    void deleteById(Long id);
    Comment selectById(Long id);
    void updateLikeCount(@Param("id") Long id, @Param("delta") int delta);
}
