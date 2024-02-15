package com.ssafy.dmobile.board.service;

import com.ssafy.dmobile.board.Dto.request.BoardRequestDTO;
import com.ssafy.dmobile.board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    BoardResponseDTO createBoard(BoardRequestDTO dto, Long memberId);

    BoardResponseDTO updateBoard(Long id, BoardRequestDTO dto, Long memberId);

    List<BoardResponseDTO> getAllBoards();

    BoardResponseDTO getBoard(Long id);

    BoardResponseDTO deleteBoard(Long id, Long memberId);

    Page<Board> findAllBoards(Pageable pageable);

    // BoardService.java
    List<BoardResponseDTO> findBoardsByTag(List<String> tags);

    // 태그 and 연산
    List<BoardResponseDTO> findBoardsByTagsAnd(List<String> tags);

    // throws IOException : 예외처리를 강제하는 코드
//    String uploadBoardImage(Long boardId, MultipartFile file, Long imageId) throws IOException;

//    void updateBoardImage(Long boardId, Long imageId, MultipartFile file) throws IOException;

//    void deleteBoardImage(Long boardId, Long imageId);

    Board createBoardWithImages(Long boardId, List<MultipartFile> images);

    List<BoardResponseDTO> searchBoards(String keyword);
}