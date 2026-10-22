package com.human.mini_prj.repository;

import com.human.mini_prj.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 날짜 내림차순으로 보드 테이블 전부 출력
    // SQL : SELECT * FROM board ORDER BY reg_time DESC
    List<Board> findAllByOrderByRegTimeDesc();

}