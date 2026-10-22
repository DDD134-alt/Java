package com.human.mini_prj.controller;

import com.human.mini_prj.dto.LoginReqDto;
import com.human.mini_prj.dto.SignUpReqDto;
import com.human.mini_prj.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j                      // Log(로그, 시스템 기록) 메시지 작성 용도
@RestController             // RESTful API(웹 표준 아키텍처)를 지원하는 컨트롤러 (직렬화(자바→JSON)/역직렬(JSON→자바)화 담당)
@RequestMapping("/auth") // http://localhost:8111/auth, (auth: Authentication(인증) 또는 Authorization(인가) 관련 기능을 모아둔 경로)
@RequiredArgsConstructor    // 생성자를 통한 Dependency Injection(디펜던시 인젝션, 의존성 주입) 자동 생성
// 프론트엔드 (React): http://localhost:3000 (포트 번호 3000)
// 백엔드 (Spring Boot): http://localhost:8111 (포트 번호 8111)
// 통신하는 주소가 다르면 브라우저는 해킹으로 여겨 데이터를 차단함으로써
// CORS(Cross-Origin Resource Sharing, 교차 출처 자원 공유) 에러 발생.
// 이를 막기 위해 @CrossOrigin으로 보안정책을 일시적으로 해제해 두 주소가 통신 할 수 있도록 하는 것
@CrossOrigin(origins = "http://localhost:3000")
 public class AuthController {
    private final AuthService authService; // 생성자를 통한 의존성 주입

    // 회원 가입 여부 확인
    // GET(조회) 방식 : URL에 정보가 실리는 방식, {} 여기에 변수(찾고싶은 이메일 주소)를 넣으면 됨
    @GetMapping("/exists/{email}")
    // @PathVariable(경로 변수) : DTO(데이터가 담긴 객체)가 아닌 데이터 자체를 보내는 방식
    public boolean existsEmail(@PathVariable String email) {
        return authService.isDuplicatedEmail(email);
    }

    // 회원 가입
    @PostMapping("/signup")
    // @RequestBody(본문 데이터) : 보안이 필요한 데이터를 DTO 형태로 보내는 방식
    // 또한 보내야 할 데이터가 많거나 복잡할 때 JSON 형대로 담아 Body(HTTP 요청의 본문 공간)에 실어 보냄
    public ResponseEntity<Boolean> signUp(@RequestBody SignUpReqDto dto) {
        return ResponseEntity.ok(authService.signUp(dto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginReqDto dto) {
        return ResponseEntity.ok(authService.login(dto.getEmail(), dto.getPwd()));
    }
}
