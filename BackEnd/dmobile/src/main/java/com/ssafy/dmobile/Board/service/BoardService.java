package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.BoardRequestDto;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDto;
import com.ssafy.dmobile.Board.repository.BoardRepository;

import java.util.List;

public interface BoardService {
    BoardResponseDto createBoard(BoardRequestDto dto);

    BoardResponseDto updateBoard(Long id, BoardRequestDto dto);

    List<BoardResponseDto> getAllBoards();

    BoardResponseDto getBoard(Long id);

    // delete 구현 못하겠음;;
//    BoardResponseDto deleteBoard(Long id);
    BoardResponseDto deleteBoard(Long id);
}
