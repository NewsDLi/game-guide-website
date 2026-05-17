package com.gameguide.service;

import com.gameguide.common.Result;
import com.gameguide.dto.GuideDTO;
import com.gameguide.dto.PageResult;
import com.gameguide.entity.Guide;
import com.gameguide.mapper.GuideMapper;
import com.gameguide.mapper.GameMapper;
import com.gameguide.mapper.TagMapper;
import com.gameguide.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class GuideService {

    private final GuideMapper guideMapper;
    private final GameMapper gameMapper;
    private final TagMapper tagMapper;

    public GuideService(GuideMapper guideMapper, GameMapper gameMapper, TagMapper tagMapper) {
        this.guideMapper = guideMapper;
        this.gameMapper = gameMapper;
        this.tagMapper = tagMapper;
    }

    public Result<?> list(Long gameId, String tag, String keyword, String orderBy, int page, int size) {
        int offset = (page - 1) * size;
        List<Guide> list = guideMapper.selectList(gameId, tag, keyword, orderBy, offset, size);
        long total = guideMapper.countList(gameId, tag, keyword);
        Map<String, Object> data = PageResult.of(total, page, size, list).toMap();
        return Result.ok(data);
    }

    public Result<?> detail(Long id, Long userId) {
        Guide guide = guideMapper.selectById(id);
        if (guide == null) {
            return Result.fail(404, "攻略不存在");
        }
        if (guide.getStatus() != 1 && (userId == null || !userId.equals(guide.getUserId()))) {
            return Result.fail(404, "攻略不存在");
        }
        guideMapper.incrementViews(id);
        guide.setViewCount(guide.getViewCount() + 1);
        return Result.ok(guide);
    }

    @Transactional
    public Result<?> create(Long userId, GuideDTO dto) {
        if (!StringUtils.hasText(dto.getTitle())) {
            return Result.fail(400, "标题不能为空");
        }
        if (!StringUtils.hasText(dto.getContent())) {
            return Result.fail(400, "内容不能为空");
        }

        Guide guide = new Guide();
        guide.setGameId(dto.getGameId());
        guide.setUserId(userId);
        guide.setTitle(dto.getTitle());
        guide.setContent(dto.getContent());
        guide.setSummary(dto.getSummary() != null ? dto.getSummary() :
                dto.getContent().replaceAll("<[^>]+>", "").length() > 100 ?
                        dto.getContent().replaceAll("<[^>]+>", "").substring(0, 100) :
                        dto.getContent().replaceAll("<[^>]+>", ""));
        guide.setTags(dto.getTags());
        guide.setCover(dto.getCover());
        guide.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);

        guideMapper.insert(guide);
        gameMapper.updateGuideCount(dto.getGameId(), 1);

        // update tag usage
        if (dto.getTags() != null) {
            for (String tagName : dto.getTags().split(",")) {
                tagName = tagName.trim();
                if (!tagName.isEmpty()) {
                    Tag tag = tagMapper.selectByName(tagName);
                    if (tag != null) {
                        tagMapper.incrementUseCount(tagName);
                    } else {
                        Tag newTag = new Tag();
                        newTag.setName(tagName);
                        newTag.setUseCount(1);
                        tagMapper.insert(newTag);
                    }
                }
            }
        }

        return Result.ok("发布成功", guide);
    }

    @Transactional
    public Result<?> update(Long id, Long userId, Integer userRole, GuideDTO dto) {
        Guide guide = guideMapper.selectById(id);
        if (guide == null) {
            return Result.fail(404, "攻略不存在");
        }
        if (!guide.getUserId().equals(userId) && userRole != 1) {
            return Result.fail(403, "无权限操作");
        }

        guide.setTitle(dto.getTitle());
        guide.setContent(dto.getContent());
        guide.setSummary(dto.getSummary());
        guide.setTags(dto.getTags());
        guide.setCover(dto.getCover());
        guide.setStatus(dto.getStatus());
        guide.setId(id);
        guideMapper.update(guide);

        return Result.ok("更新成功", guideMapper.selectById(id));
    }

    @Transactional
    public Result<?> delete(Long id, Long userId, Integer userRole) {
        Guide guide = guideMapper.selectById(id);
        if (guide == null) {
            return Result.fail(404, "攻略不存在");
        }
        if (!guide.getUserId().equals(userId) && userRole != 1) {
            return Result.fail(403, "无权限操作");
        }

        guideMapper.deleteById(id);
        gameMapper.updateGuideCount(guide.getGameId(), -1);
        return Result.ok("删除成功", null);
    }

    public Result<?> myGuides(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        List<Guide> list = guideMapper.selectByUserId(userId, offset, size);
        long total = guideMapper.countByUserId(userId);
        Map<String, Object> data = PageResult.of(total, page, size, list).toMap();
        return Result.ok(data);
    }
}
