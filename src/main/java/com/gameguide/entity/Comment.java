package com.gameguide.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Comment {
    private Long id;              // 评论ID
    private Long guideId;         // 关联攻略ID
    private Long userId;          // 评论用户ID
    private Long parentId;        // 父评论ID
    private Long replyUserId;     // 被回复用户ID
    private String content;       // 评论内容
    private Integer likeCount;    // 评论点赞数
    private LocalDateTime createdAt;  // 创建时间

    // 关联查询字段
    private String nickname;      // 用户昵称
    private String avatar;        // 用户头像
    private String replyNickname; // 被回复用户昵称
    private List<Comment> replies; // 子回复列表

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getGuideId() { return guideId; }
    public void setGuideId(Long guideId) { this.guideId = guideId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public Long getReplyUserId() { return replyUserId; }
    public void setReplyUserId(Long replyUserId) { this.replyUserId = replyUserId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Integer getLikeCount() { return likeCount; }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getReplyNickname() { return replyNickname; }
    public void setReplyNickname(String replyNickname) { this.replyNickname = replyNickname; }
    public List<Comment> getReplies() { return replies; }
    public void setReplies(List<Comment> replies) { this.replies = replies; }
}
