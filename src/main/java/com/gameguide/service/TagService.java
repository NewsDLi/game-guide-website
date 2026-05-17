package com.gameguide.service;

import com.gameguide.common.Result;
import com.gameguide.mapper.TagMapper;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagMapper tagMapper;

    public TagService(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    public Result<?> hot(int limit) {
        return Result.ok(tagMapper.selectHot(limit));
    }

    public Result<?> search(String keyword, int limit) {
        return Result.ok(tagMapper.search(keyword, limit));
    }
}
