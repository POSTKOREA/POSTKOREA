package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.BoardRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.Board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface BoardService {
    BoardResponseDTO createBoard(BoardRequestDTO dto, Long memberId);

    BoardResponseDTO updateBoard(Long id, BoardRequestDTO dto, Long memberId);

    List<BoardResponseDTO> getAllBoards();

    BoardResponseDTO getBoard(Long id);

    BoardResponseDTO deleteBoard(Long id, Long memberId);

    Page<Board> findAllBoards(Pageable pageable);
}
