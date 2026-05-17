package com.gameguide.entity;

import java.time.LocalDateTime;

public class Like {
    private Long id;              // 点赞ID
    private Long userId;          // 点赞用户ID
    private Long targetId;        // 目标ID
    private Integer targetType;   // 1=攻略 2=评论
    private LocalDateTime createdAt;  // 创建时间

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public Integer getTargetType() { return targetType; }
    public void setTargetType(Integer targetType) { this.targetType = targetType; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
