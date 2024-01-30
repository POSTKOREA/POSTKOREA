package com.ssafy.dmobile.Board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "Boards")
public class Board {

    // 게시글 ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    // title(제목)
    @Column(nullable = false)
    private String title;

    // post_content(내용)
    @Column(nullable = false)
    private String content;

    // User(유저 ID)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    // post_image(이미지 업로드)

    // post_created(작성시간)
    private LocalDateTime Board_created;

    // 태그
    @OneToMany(mappedBy = "board")
    private Set<Tag> tags = new HashSet<>();

    // 게시글 좋아요 기능 필요한지..? -> 좋아요 엔티티 새로 만들어야 함.
//     @OneToOne(mappedBy = "board")
//    private Likeboard likeboard;

    // 댓글
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<Comment> comments;
}
