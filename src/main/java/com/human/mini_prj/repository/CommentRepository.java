package com.human.mini_prj.repository;

import com.human.mini_prj.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 게시글에 대한 댓글 목록 최신순으로 조회
    // SQL : SELECT * FROM ORDER BY comment DESC
    List<Comment> findByBoardIdOrderByRegDateDesc(Long boardId);
}
