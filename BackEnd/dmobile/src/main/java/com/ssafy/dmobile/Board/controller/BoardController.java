package com.ssafy.dmobile.Board.controller;

import com.ssafy.dmobile.Board.Dto.request.BoardRequestDto;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDto;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.service.BoardService;
import com.ssafy.dmobile.Board.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BoardResponseDto> registerBoard(@RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto);
        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return new ResponseEntity(boardResponseDto, HttpStatus.OK);
    }

    // 게시판 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> modifyBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.updateBoard(id, boardRequestDto);
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        List<BoardResponseDto> dtoList = boardService.getAllBoards();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> findBoard(@PathVariable long id) {
        BoardResponseDto boardResponseDto = boardService.getBoard(id);
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    // 삭제 기능 제대로 안되고 있음
//    @DeleteMapping("/{id}")
//    public ResponseEntity<BoardResponseDto> deleteBoard(@PathVariable long id) {
//        BoardService.delete(id);
//        return ResponseEntity.ok().build();
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BoardResponseDto> deleteBoard(@PathVariable long id) {
        BoardResponseDto deletedBoard = boardService.deleteBoard(id);
        return ResponseEntity.ok(deletedBoard);
    }


}
