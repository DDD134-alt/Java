package com.human.mini_prj.dto;

// record : 읽기만 가능한 불변 객체를 만드는 문법
public record TokenDto(String grantType, String accessToken, long accessTokenExpiresIn) {
    // 아무것도 없어도 됨
}
