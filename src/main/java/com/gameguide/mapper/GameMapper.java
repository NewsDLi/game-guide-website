package com.gameguide.mapper;

import com.gameguide.entity.Game;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GameMapper {
    List<Game> selectList(@Param("category") String category,
                          @Param("offset") int offset,
                          @Param("size") int size);
    long countByCategory(@Param("category") String category);
    Game selectById(Long id);
    void updateGuideCount(@Param("id") Long id, @Param("delta") int delta);
}
