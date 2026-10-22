package com.human.mini_prj.entity;

// 게시글 작성

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
public class Board {
    // 고유 식별 자동 증가 번호 id
    @Id                                                  // Primary Key
    @GeneratedValue(strategy =  GenerationType.IDENTITY) // Auto Increment(순서대로 번호 자동 증가)
    @Column(name = "board_id")                           // 테이블 목록 이름 지정
    private Long id;                                     // 위 어노테이션의 영향을 받는 id 변수

    // 게시글 내용
    @Lob
    @Column(length = 10000)
    private String content;

    // 게시글 제목
    @Column(length = 100, nullable = false)
    private String title;

    // 작성일
    private LocalDateTime regTime;
    @PrePersist
    public void prePersist() {
        this.regTime = LocalDateTime.now();
    }

    // 게시물과 한 명의 회원이 다대일 관계
    // 회원(member_id) 한 명이 여러 게시물(board)를 만들 수 있다는 뜻
    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관걔
    @JoinColumn(name = "member_id")
    private Member member;
}
