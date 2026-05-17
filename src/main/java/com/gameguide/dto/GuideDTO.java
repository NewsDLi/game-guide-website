package com.gameguide.dto;

public class GuideDTO {
    private Long gameId;
    private String title;
    private String content;
    private String summary;
    private String tags;
    private String cover;
    private Integer status;

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getCover() { return cover; }
    public void setCover(String cover) { this.cover = cover; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
