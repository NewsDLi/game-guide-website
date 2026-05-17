package com.gameguide.entity;

import java.time.LocalDateTime;

public class Tag {
    private Integer id;           // 标签ID
    private String name;          // 标签名称
    private Integer useCount;     // 使用次数
    private LocalDateTime createdAt;  // 创建时间

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getUseCount() { return useCount; }
    public void setUseCount(Integer useCount) { this.useCount = useCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
