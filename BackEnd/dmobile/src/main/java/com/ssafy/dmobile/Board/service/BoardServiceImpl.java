package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.BoardRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service  // 빈으로 등록
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor  // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public BoardResponseDTO createBoard(BoardRequestDTO dto) {
        Board board = dto.dtoToEntity(dto);
        Board save = boardRepository.save(board);
        return new BoardResponseDTO(save);
    }

    @Override
    @Transactional
    public BoardResponseDTO updateBoard(Long id, BoardRequestDTO dto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));
        board.update(dto.getTitle(), dto.getContent());
        Board save = boardRepository.save(board);
        return new BoardResponseDTO(save);
    }

    @Override
    // 전체 조회 repository.findAll();
    public List<BoardResponseDTO> getAllBoards() {
        List<Board> list = boardRepository.findAll();
//        list.sort((a, b) -> a.g);
        List<BoardResponseDTO> dtoList = new ArrayList<>();
        list.stream().forEach(findAll -> dtoList.add(new BoardResponseDTO(findAll)));
        return dtoList;
    }

    @Override
    public BoardResponseDTO getBoard(Long id) {
        Optional<Board> optionalboard = boardRepository.findById(id);
        return optionalboard.map(BoardResponseDTO::new).orElse(null);
    }

    @Override
    @Transactional
    public BoardResponseDTO deleteBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isEmpty()) {
            throw new RuntimeException("Board not found for deletion");
        } else {
            Board deleteBoard = optionalBoard.get();
            boardRepository.deleteById(id);
            return new BoardResponseDTO(deleteBoard);
        }
    }

}
