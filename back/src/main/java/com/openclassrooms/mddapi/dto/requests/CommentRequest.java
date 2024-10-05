package com.openclassrooms.mddapi.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private Long id;
    private String content;
    private Long articleId;
    private Long userId;
}
