package com.bol.post;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {
    private final PostRepository repository;

    public PostController(PostRepository repository) { this.repository = repository; }

    @GetMapping("/posts")
    public List<Post> feed(@RequestParam(defaultValue = "false") boolean bookmarks) {
        return bookmarks ? repository.findByBookmarkedTrueOrderByCreatedAtDesc()
                         : repository.findByParentPostIdIsNullOrderByCreatedAtDesc();
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@Valid @RequestBody CreatePostRequest request) {
        if (request.parentPostId() != null && !repository.existsById(request.parentPostId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent post not found");
        }
        Post saved = repository.save(new Post(request.content().trim(), "Zeeshan Shaikh",
            "@proteinshaikhs", null, request.parentPostId()));
        if (request.parentPostId() != null) {
            Post parent = repository.findById(request.parentPostId()).orElseThrow();
            parent.setRepliesCount(parent.getRepliesCount() + 1);
            repository.save(parent);
        }
        return saved;
    }

    @GetMapping("/posts/{id}/replies")
    public List<Post> replies(@PathVariable Long id) {
        requirePost(id);
        return repository.findByParentPostIdOrderByCreatedAtAsc(id);
    }

    @PostMapping("/posts/{id}/{action}")
    public Post action(@PathVariable Long id, @PathVariable String action) {
        Post post = requirePost(id);
        switch (action) {
            case "like" -> { post.setLiked(!post.isLiked()); post.setLikesCount(post.getLikesCount() + (post.isLiked() ? 1 : -1)); }
            case "repost" -> { post.setReposted(!post.isReposted()); post.setRepostsCount(post.getRepostsCount() + (post.isReposted() ? 1 : -1)); }
            case "bookmark" -> post.setBookmarked(!post.isBookmarked());
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown action");
        }
        return repository.save(post);
    }

    private Post requirePost(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }
}
