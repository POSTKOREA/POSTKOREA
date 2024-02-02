package com.ssafy.dmobile.Board.controller;

import com.ssafy.dmobile.Board.Dto.request.CommentRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDTO;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.entity.Comment;
import com.ssafy.dmobile.Board.service.BoardService;
import com.ssafy.dmobile.Board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long boardId,
                                                                @RequestBody CommentRequestDTO commentRequestDTO) {
        commentRequestDTO.setBoardId(boardId);
        CommentResponseDTO createdComment = commentService.createComment(commentRequestDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @PutMapping("/{boardId}/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO updatedComment = commentService.updateComment(commentId, commentRequestDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // 개별 삭제
    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable long commentId) {
        CommentResponseDTO deletedComment = commentService.deleteComment(commentId);
        return new ResponseEntity<>(deletedComment, HttpStatus.OK);
    }

    // 단건 조회
    @GetMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> getComment(@PathVariable Long commentId) {
        CommentResponseDTO comment = commentService.getComment(commentId);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getAllComments(@PathVariable Long boardId) {
        List<CommentResponseDTO> comments = commentService.getAllComments(boardId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
