package com.human.mini_prj.security;

import com.human.mini_prj.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// 현제 로그인 되어있는 사용자 꺼내기
// 토큰을 발행할때 이메일을 넣도록함, 이때 이메일 정보가 심겨지기 때문에 이메일을 뺀다고 한다
public final class SecurityUtil {
    private SecurityUtil() {}

    public static String currentEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // permitAll 경로에서는 "anonymousUser"가 들어 있으므로 함께 걸러냄
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "로그인이 필요합니다.");
        }
        return auth.getName();   // 토큰의 sub = email
    }
}