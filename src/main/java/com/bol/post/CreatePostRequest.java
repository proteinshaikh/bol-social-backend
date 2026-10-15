package com.bol.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
    @NotBlank @Size(max = 280) String content,
    Long parentPostId
) {}
