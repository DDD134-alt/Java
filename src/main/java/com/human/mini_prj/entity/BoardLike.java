package com.human.mini_prj.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
// board_id(게시글 번호)와 member_id(회원 번호)를 합친 값이 데이터베이스 안에서 오직 단 하나만 존재하도록 강제
// 회원 한 명이 동일한 게시글에 두 번 이상 좋아요를 누를 수 없도록 막아주는 어노테이션
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"board_id", "member_id"}))
public class BoardLike {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "board_like_id")
    private Long id;

    // @ManyToOne : 다대일 관계
    // fetch = FetchType.LAZY : 필요한 시점에 데이터를 가져오도록 대기(지연)하는 기술
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 좋아요가 생성(저장)되는 시각을 담아둘 변수 (필드)
    private LocalDateTime regDate;
    // 동작형(콜백, Callback) 어노테이션 : 데이터베이스에 저장(Persist)되기 직전에 이 메서드를
    // 시스템이 자동으로 호출하여, 현재 시각(LocalDateTime.now())을 regDate 변수에 채워줌!
    @PrePersist
    public void prePersist() {
        regDate = LocalDateTime.now();
    }
}
