package com.ssafy.dmobile.Board.Dto.response;

import com.ssafy.dmobile.Board.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentResponseDto {
    private Long id;
    private Long boardId;
    private String content;
    private Long parentId;
    private Boolean hasChildren;
    private LocalDateTime createCommentTime;
    private List<CommentResponseDto> children;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId();
        if (comment.getParent() == null) {
            this.parentId = null;
        } else {
            this.parentId = comment.getParent().getId();
        }
        this.createCommentTime = comment.getCreateCommentTime();
    }
}
