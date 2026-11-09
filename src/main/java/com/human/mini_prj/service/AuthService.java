package com.human.mini_prj.service;

import com.human.mini_prj.dto.SignUpReqDto;
import com.human.mini_prj.dto.TokenDto;
import com.human.mini_prj.entity.Member;
import com.human.mini_prj.exception.CustomException;
import com.human.mini_prj.repository.MemberRepository;
import com.human.mini_prj.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j                   // log 메시지 출력을 위해 사용
@Service                 // Spring Container에 Bean 등록, 없어도 괜찮긴 함
@Transactional           // 트랜잭션 처리 : 여러개의 물리적인 작업 단위를 한개의 논리적인 단위로 묶음, 실패시 롤백 하는 기능
@RequiredArgsConstructor // 생성자를 통한 의존성 주입을 자동으로 만들어줌
public class AuthService {
    private final MemberRepository memberRepository; // 생성자를 통한 의존성
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원가입 여부 확인
    public boolean isDuplicatedEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원가입
    public boolean signUp(SignUpReqDto dto) {
        try {
            Member member = toEntity(dto);
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            log.error("회원 가입 시 오류 발생 {}", e.getMessage());
            return false;
        }
    }

//    // 기존 로그인
//    public boolean login(String email, String pwd) {
//        Optional<Member> member = memberRepository.findByEmailAndPwd(email, pwd);
//        return member.isPresent();
//    }

    // 로그인 : 성공 → 토큰, 실패 → 401
    @Transactional(readOnly = true)
    public TokenDto login(String email, String pwd) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(this::loginFailed);
        if (!passwordEncoder.matches(pwd, member.getPwd())) {   // 평문 vs 해시 비교
            throw loginFailed();
        }
        return tokenProvider.createToken(member.getEmail(), member.getAuthority());
    }

    private CustomException loginFailed() {
        // 이메일/비밀번호 중 무엇이 틀렸는지 알려주지 않는 것이 보안상 안전
        return new CustomException(HttpStatus.UNAUTHORIZED, "LOGIN_FAILED", "이메일 또는 비밀번호가 올바르지 않습니다.");
    }


    // DTO > Entity
    private Member toEntity(SignUpReqDto dto) {
        Member member = new Member();
        member.setName(dto.getName());
        member.setPwd(dto.getPwd());
        member.setEmail(dto.getEmail());
        return member;
    }
}
