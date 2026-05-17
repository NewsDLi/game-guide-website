package com.gameguide.mapper;

import com.gameguide.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    Like selectByUserAndTarget(@Param("userId") Long userId,
                                @Param("targetId") Long targetId,
                                @Param("targetType") Integer targetType);
    void insert(Like like);
    void deleteById(Long id);
    int countByTarget(@Param("targetId") Long targetId,
                      @Param("targetType") Integer targetType);
}
