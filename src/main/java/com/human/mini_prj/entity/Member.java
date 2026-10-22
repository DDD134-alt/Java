package com.human.mini_prj.entity;

// 에티티(entity) : 데이터베이스 테이블과 매핑되는 설계도/클래스

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity                    // @Entity는 SQL의 Table이라고도 함, 행은 객체(new), 열은 인스턴스 필드, 퀴리분은 메서드 이름
@Table(name = "member")    // 궅이 쓰지 않아도 되지만 안 하면 카펠표기법이 스네이크표기법으로 자동 바뀜
@Getter                    // 게터 메서드 자동 생성
@Setter                    // 세터 메서드 자동 생성
@NoArgsConstructor         // 매개변수가 없는 생성자 자동 생성
@ToString(exclude = "pwd") // 오버라이딩으로 정보 출력시 비밀번호 제외 하기

public class Member {
    @Id                       // PK(Primary Key) 기본키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 생성전략을 따름 (MySQL의 Auto Increment 활용)
    @Column(name = "member_id") // DB 테이블에 만들어질 컬럼(데이터 각 항목의 이름표)을 member_id로 지정
    private Long id;

    @Column(length = 100)     // 이름 필드 길이 제한 (VARCHAR 100)
    private String name;

    @Column(nullable = false) // 비밀번호는 필수 필드 (NOT NULL)
    private String pwd;       // 비밀번호 저장시 해시 암호화 적용 필요

    @Column(unique = true, length = 150) // 이메일 유니크 제약 조건 및 길이 제한 (중복 불가)
    private String email;

    @Column(length = 255)     // 이미지 URL/ 경로 길이 제한
    private String image;

    private LocalDateTime regDate; // java.util.Date 대신 java8 날짜/시간 API 사용

    @PrePersist               // PrePersist : DB에 INSERT 되기 전에 실행하겠다는 메서드
    public void prePersist() {
        this.regDate = LocalDateTime.now(); // 현재 날짜 및 시간으로 초기화
    }
}
