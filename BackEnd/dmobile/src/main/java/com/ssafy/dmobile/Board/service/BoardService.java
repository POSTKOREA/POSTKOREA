package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.BoardRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDTO;

import java.util.List;

public interface BoardService {
    BoardResponseDTO createBoard(BoardRequestDTO dto);

    BoardResponseDTO updateBoard(Long id, BoardRequestDTO dto);

    List<BoardResponseDTO> getAllBoards();

    BoardResponseDTO getBoard(Long id);

    BoardResponseDTO deleteBoard(Long id);
}
