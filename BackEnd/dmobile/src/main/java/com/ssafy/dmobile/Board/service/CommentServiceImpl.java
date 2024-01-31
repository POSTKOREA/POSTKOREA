package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.BoardRequestDto;
import com.ssafy.dmobile.Board.Dto.request.CommentRequestDto;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDto;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDto;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.entity.Comment;
import com.ssafy.dmobile.Board.repository.BoardRepository;
import com.ssafy.dmobile.Board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
//    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto dto, Long boardId, String email) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->)
        return new CommentResponseDto(save);
    }

}
