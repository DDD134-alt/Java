package com.human.mini_prj.controller;

import com.human.mini_prj.dto.MemberResDto;
import com.human.mini_prj.dto.MemberUpdateReqDto;
import com.human.mini_prj.dto.PageResDto;
import com.human.mini_prj.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member") // http://localhost:8111/member
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberResDto>> memberList() {
        return ResponseEntity.ok(memberService.getMemberList());
    }

    // 회원 상세 조회 : GET http://localhost:8111/member/{email}
    @GetMapping("/{email}")
    public ResponseEntity<MemberResDto> memberDetail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(memberService.getMemberDetail(email));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();  // 404
        }
    }

    // 회원 수정 : PUT http://localhost:8111/member/modify
    @PutMapping("/modify")
    public ResponseEntity<Boolean> memberModify(@RequestBody MemberUpdateReqDto dto) {
        return ResponseEntity.ok(memberService.updateMember(dto));
    }

    // 회원 삭제 : DELETE http://localhost:8111/member/{email}
    @DeleteMapping("/{email}")
    public ResponseEntity<Boolean> memberDelete(@PathVariable String email) {
        return ResponseEntity.ok(memberService.deleteMember(email));
    }

    // 회원목록 페이지네이션 조회
    // GET : @RequestParam(리퀘스트파람) 방식 -> http://localhost:8111/member/List?page=1&size=10
    @GetMapping("/list/page")
    public ResponseEntity<PageResDto<MemberResDto>> memberListPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(memberService.getMemberPage(page-1, size));
    }
}
