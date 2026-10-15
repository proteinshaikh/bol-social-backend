package com.bol.post;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByParentPostIdIsNullOrderByCreatedAtDesc();
    List<Post> findByBookmarkedTrueOrderByCreatedAtDesc();
    List<Post> findByParentPostIdOrderByCreatedAtAsc(Long parentPostId);
}
