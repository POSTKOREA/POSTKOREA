package com.ssafy.dmobile.Board.repository;

import com.ssafy.dmobile.Board.entity.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // JpaRepository를 상속받는다.
    Page<Board> findAll(Pageable pageable);
}
