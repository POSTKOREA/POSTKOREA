package com.ssafy.dmobile.Board.controller;

import com.ssafy.dmobile.Board.Dto.request.CommentRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDTO;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.entity.Comment;
import com.ssafy.dmobile.Board.service.BoardService;
import com.ssafy.dmobile.Board.service.CommentService;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
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
    private final AuthTokensGenerator authTokensGenerator;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long boardId,
                                                            @RequestBody CommentRequestDTO commentRequestDTO,
                                                            @RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        commentRequestDTO.setBoardId(boardId);
        CommentResponseDTO commentResponseDTO = commentService.createComment(commentRequestDTO, memberId);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{boardId}/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentRequestDTO commentRequestDTO,
                                                            @RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        CommentResponseDTO commentResponseDTO = commentService.updateComment(commentId, commentRequestDTO, memberId);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    // 개별 삭제
    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable long commentId,
                                                            @RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.noContent().build();
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
