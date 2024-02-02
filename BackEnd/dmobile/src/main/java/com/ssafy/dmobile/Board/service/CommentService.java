package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.CommentRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDTO;
import com.ssafy.dmobile.Board.entity.Comment;
import org.apache.catalina.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
//    CommentResponseDTO createComment(Long boardId, CommentRequestDTO dto);
//    CommentResponseDTO deleteComment(Long commentId);
//    CommentResponseDTO updateComment(Long commentId, CommentRequestDTO dto);

    // 댓글 목록 확인
//    CommentResponseDTO comments(Long boardid);
    CommentResponseDTO createComment(CommentRequestDTO dto);

    CommentResponseDTO deleteComment(Long commentId);

    // 갱신
    CommentResponseDTO updateComment(Long commentId, CommentRequestDTO dto);

    // 전체 조회
    List<CommentResponseDTO> getAllComments(Long boardId);

    // 댓글 하나 조회
    CommentResponseDTO getComment(Long id);
}
