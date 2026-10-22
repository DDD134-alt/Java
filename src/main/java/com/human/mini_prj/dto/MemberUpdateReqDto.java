package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateReqDto {
    // 회원 정보 수정 요청 DTO
    private String email; // 수정 대상 회원을 식별하는 값 (수정 불가)
    private String name;  // 수정할 수 있는 목록
    private String image; // 이미지 서버를 따로 만들고 그 주소(경로)를 DB에 넣는 방식을 주로 사용
}