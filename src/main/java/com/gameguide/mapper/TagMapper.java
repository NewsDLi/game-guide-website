package com.gameguide.mapper;

import com.gameguide.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper {
    List<Tag> selectHot(@Param("limit") int limit);
    List<Tag> search(@Param("keyword") String keyword, @Param("limit") int limit);
    Tag selectByName(String name);
    void insert(Tag tag);
    void incrementUseCount(String name);
}
