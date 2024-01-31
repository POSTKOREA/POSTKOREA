package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.CommentRequestDto;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {
    // 댓글 작성
    CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long boardId, String email);

    // 댓글 조회
    List<CommentResponseDto> commentList(Long id);

    // 댓글 수정
    void updateComment(CommentRequestDto commentRequestDto, Long commentId);

    // 댓글 삭제
    void deleteComment(Long commentId);
}
