package com.gameguide.mapper;

import com.gameguide.entity.Guide;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GuideMapper {
    List<Guide> selectList(@Param("gameId") Long gameId,
                           @Param("tag") String tag,
                           @Param("keyword") String keyword,
                           @Param("orderBy") String orderBy,
                           @Param("offset") int offset,
                           @Param("size") int size);
    long countList(@Param("gameId") Long gameId,
                   @Param("tag") String tag,
                   @Param("keyword") String keyword);
    Guide selectById(Long id);
    void insert(Guide guide);
    void update(Guide guide);
    void deleteById(Long id);
    void incrementViews(Long id);
    void updateLikeCount(@Param("id") Long id, @Param("delta") int delta);
    void updateCommentCount(@Param("id") Long id, @Param("delta") int delta);
    List<Guide> selectByUserId(@Param("userId") Long userId,
                               @Param("offset") int offset,
                               @Param("size") int size);
    long countByUserId(Long userId);
}
