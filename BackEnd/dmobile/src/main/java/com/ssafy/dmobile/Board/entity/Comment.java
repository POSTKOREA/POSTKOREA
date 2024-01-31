package com.ssafy.dmobile.Board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    // User
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user

    // board_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Board_id", nullable = false)
    private Board board;

    // content
    @Column(nullable = false)
    private String content;

    // createdTime
    private LocalDateTime createTime;


    public void update(String content) {
        this.content = content;
    }
}
