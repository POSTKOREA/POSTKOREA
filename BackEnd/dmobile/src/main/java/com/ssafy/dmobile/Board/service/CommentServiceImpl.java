package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.CommentRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDTO;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.entity.Comment;
import com.ssafy.dmobile.Board.repository.BoardRepository;
import com.ssafy.dmobile.Board.repository.CommentRepository;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public CommentResponseDTO createComment(CommentRequestDTO dto) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + dto.getBoardId()));
//        Member member = memberRepository.findById(dto.getMemberId())
//                .orElseThrow(() -> new RuntimeException("Member not found with id: " + dto.getMemberId()));
        Comment comment = dto.dtoToEntity(dto);
        comment.setBoard(board);
        Comment saveComment = commentRepository.save(comment);
//        comment.setMember(member);

        return new CommentResponseDTO(saveComment);
    }

    @Override
    @Transactional
    public CommentResponseDTO deleteComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new RuntimeException("Board not found for deletion");
        } else {
            Comment deleteComment = optionalComment.get();
            commentRepository.deleteById(commentId);
            return new CommentResponseDTO(deleteComment);
        }
    }

    @Override
    @Transactional
    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO dto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.update(dto.getContent());
        Comment save = commentRepository.save(comment);
        return new CommentResponseDTO(save);
    }

    @Override
    @Transactional
    public CommentResponseDTO getComment(Long boardId) {
        Optional<Comment> optionalComment = commentRepository.findById(boardId);
        return optionalComment.map(CommentResponseDTO::new).orElse(null);
    }

    @Override
    @Transactional
    public List<CommentResponseDTO> getAllComments(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardId(boardId);
        List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponseDTOS.add(new CommentResponseDTO(comment));
        }
        return commentResponseDTOS;
    }

}
