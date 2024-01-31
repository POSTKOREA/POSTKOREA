package com.ssafy.dmobile.Board.Dto.request;

import com.ssafy.dmobile.Board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {

    private Long id;
    private Long boardId;
    private String content;
    private Long parentId;

    // Dto -> Entity
    public Comment dtoToEntity(CommentRequestDto dto) {
        return Comment.builder()
                .id(id)
                .content(content)
                .createCommentTime(LocalDateTime.now())
                .build();
    }
}
