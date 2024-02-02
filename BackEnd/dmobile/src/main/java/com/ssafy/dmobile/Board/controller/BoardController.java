package com.ssafy.dmobile.Board.controller;

import com.ssafy.dmobile.Board.Dto.request.BoardRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDTO;
import com.ssafy.dmobile.Board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시판 작성
    @PostMapping
    // requestbody로 요청 받은 본문 값 매핑
    public ResponseEntity<BoardResponseDTO> registerBoard(@RequestBody BoardRequestDTO boardRequestDto) {
        BoardResponseDTO boardResponseDto = boardService.createBoard(boardRequestDto);
        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    // 게시판 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> modifyBoard(@PathVariable Long boardId, @RequestBody BoardRequestDTO boardRequestDto) {
        BoardResponseDTO boardResponseDto = boardService.updateBoard(boardId, boardRequestDto);
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getAllBoards() {
        List<BoardResponseDTO> dtoList = boardService.getAllBoards();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    // 게시글 단건 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> findBoard(@PathVariable long boardId, Model model) {
        BoardResponseDTO dto = boardService.getBoard(boardId);
//        List<CommentResponseDTO> comments = dto.getComments();
//        if (comments != null && comments.isEmpty()) {
//            model.addAttribute("comments", comments);
//        }
//        model.addAttribute("boards", dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> deleteBoard(@PathVariable long boardId) {
        BoardResponseDTO deletedBoard = boardService.deleteBoard(boardId);
        return ResponseEntity.ok(deletedBoard);
    }


}
