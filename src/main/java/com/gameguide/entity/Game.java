package com.gameguide.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Game {
    private Long id;              // 游戏ID
    private String name;          // 游戏名称
    private String cover;         // 封面图URL
    private String category;      // 分类
    private String description;   // 游戏简介
    private BigDecimal rating;    // 评分
    private Integer guideCount;   // 攻略数量
    private Integer status;       // 0=下架 1=上架
    private Integer sortOrder;    // 排序权重
    private LocalDateTime createdAt;  // 创建时间
    private LocalDateTime updatedAt;  // 更新时间

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCover() { return cover; }
    public void setCover(String cover) { this.cover = cover; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }
    public Integer getGuideCount() { return guideCount; }
    public void setGuideCount(Integer guideCount) { this.guideCount = guideCount; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
