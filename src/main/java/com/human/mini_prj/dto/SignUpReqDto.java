package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignUpReqDto {
    // 회원 가입 요청 DTO
    private String name;
    private String pwd;
    private String email;
}
