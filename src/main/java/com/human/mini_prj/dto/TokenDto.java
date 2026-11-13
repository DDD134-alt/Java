package com.human.mini_prj.dto;

// record : 읽기만 가능한 불변 객체를 만드는 문법
public record TokenDto(String grantType,
                       String accessToken,
                       long accessTokenExpiresIn,
                       String refreshToken,
                       long refreshTokenExpiresIn) {
    // 괄호 안 자료형에 자동으로 final이 붙음
}
