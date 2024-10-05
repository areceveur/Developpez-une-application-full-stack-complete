package com.openclassrooms.mddapi.dto.response;

import com.openclassrooms.mddapi.models.DBComments;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {
    private Long id;
    private String comment;
    private String username;

    public CommentResponse(DBComments comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.username = comment.getUser() != null ? comment.getUser().getUsername(): "Utilisateur inconu";
    }
}