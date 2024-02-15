package com.ssafy.dmobile.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.dmobile.board.entity.QBoardTag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardTagRepositoryImpl implements CustomBoardTagRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByBoardId(Long boardId) {
        QBoardTag qBoardTag = QBoardTag.boardTag;
        queryFactory.delete(qBoardTag)
                .where(qBoardTag.board.boardId.eq(boardId))
                .execute();
    }
}