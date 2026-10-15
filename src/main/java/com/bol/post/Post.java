package com.bol.post;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "posts")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 280)
    private String content;
    @Column(nullable = false)
    private String authorName;
    @Column(nullable = false)
    private String authorHandle;
    private String avatar;
    private Instant createdAt = Instant.now();
    private Long parentPostId;
    private long likesCount;
    private long repostsCount;
    private long repliesCount;
    private boolean liked;
    private boolean reposted;
    private boolean bookmarked;

    protected Post() {}

    public Post(String content, String authorName, String authorHandle, String avatar, Long parentPostId) {
        this.content = content;
        this.authorName = authorName;
        this.authorHandle = authorHandle;
        this.avatar = avatar;
        this.parentPostId = parentPostId;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public String getAuthorName() { return authorName; }
    public String getAuthorHandle() { return authorHandle; }
    public String getAvatar() { return avatar; }
    public Instant getCreatedAt() { return createdAt; }
    public Long getParentPostId() { return parentPostId; }
    public long getLikesCount() { return likesCount; }
    public long getRepostsCount() { return repostsCount; }
    public long getRepliesCount() { return repliesCount; }
    public boolean isLiked() { return liked; }
    public boolean isReposted() { return reposted; }
    public boolean isBookmarked() { return bookmarked; }
    public void setLiked(boolean value) { liked = value; }
    public void setReposted(boolean value) { reposted = value; }
    public void setBookmarked(boolean value) { bookmarked = value; }
    public void setLikesCount(long value) { likesCount = Math.max(0, value); }
    public void setRepostsCount(long value) { repostsCount = Math.max(0, value); }
    public void setRepliesCount(long value) { repliesCount = Math.max(0, value); }
}
