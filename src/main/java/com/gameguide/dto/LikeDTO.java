package com.gameguide.dto;

public class LikeDTO {
    private Long targetId;
    private Integer targetType;

    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public Integer getTargetType() { return targetType; }
    public void setTargetType(Integer targetType) { this.targetType = targetType; }
}
