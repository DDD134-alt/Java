package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter // board.getname 코드 작성시 public String getName() { return this.name; }를 생략하게 해줌
@Setter // board.setname 코드 작성시 public void String setName() { this.name = name; }를 생략하게 해줌
@NoArgsConstructor // 자동으로 기본 생성자 public board() {}를 생성
public class BoardResDto {
    private Long id;
    private String title;
    private String content;
    private String email;
    private String name;
    private LocalDateTime regDate;
}
