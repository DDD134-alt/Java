package com.human.mini_prj.repository;

import com.human.mini_prj.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    // 전체 게시글 날짜 내림차순으로 조회
    // SQL :
    // SELECT *
    // FROM board_like BL
    // JOIN board B
    // ON BL.board_id = B.board_id
    // JOIN member M
    // ON BL.member_id = M.member_id
    // WHERE B.board_id = ? AND M.member_email = ?
    Optional<BoardLike> findByBoard_IdAndMember_Email(Long boardId, String email);

    // 게시글 좋아요의 총 개수
    // SQL : SELECT COUNT(*) FROM board_like WHERE board_id = ?
    long countByBoardId(Long boardId);

}
