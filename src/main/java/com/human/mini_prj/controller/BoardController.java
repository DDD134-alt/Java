package com.human.mini_prj.controller;

import com.human.mini_prj.dto.BoardResDto;
import com.human.mini_prj.dto.BoardWriteReqDto;
import com.human.mini_prj.dto.PageResDto;
import com.human.mini_prj.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j                       // Log 메시지 작성 용도
@RestController              // Restfull API를 지원하는 컨트롤러(직렬화/역직렬화) 기능을 가지고 있음
@RequestMapping("/board") // http://localhost:8111/board
@RequiredArgsConstructor     // 생성자를 통한 의존성 주입을 자동 생성
@CrossOrigin(origins = "http://localhost:3000")
public class BoardController {
    private final BoardService boardService;

    // 게시글 전체 조회
    @GetMapping("/list")
    // 객체를 응답으로 반환
    public ResponseEntity<List<BoardResDto>> boardList() {
        List<BoardResDto> list = boardService.getBoardList();
        return ResponseEntity.ok(list); // .ok 성공이라는 뜻
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResDto> boardDetail(@PathVariable Long id) {
        BoardResDto boardResDto = boardService.getBoardDetail(id);
        return ResponseEntity.ok(boardResDto);
    }

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Boolean> boardWrite(@RequestBody BoardWriteReqDto dto) {
        boolean result = boardService.writeBoard(dto);
        return ResponseEntity.ok(result);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> boardModify(@PathVariable Long id, @RequestBody BoardWriteReqDto dto) {
        boolean result = boardService.updateBoard(id, dto);
        return ResponseEntity.ok(result);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> boardDelete(@PathVariable Long id) {
        boolean result = boardService.deleteBoard(id);
        return ResponseEntity.ok(result);
    }

    // 게시글 목록 페이지네이션 조회
    // GET : @RequestParam(리퀘스트파람) 방식 -> http://localhost:8111/board/List?page=1&size=10
    @GetMapping("/list/page")
    public ResponseEntity<PageResDto<BoardResDto>> boardListPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // page는 0부터 시작하기 때문에 검색할때 편하도록 page에 -1을 넣어둠
        return ResponseEntity.ok(boardService.getBoardPage(page-1, size));
    }

    // 게시글 좋아요
//    @PostMapping("/{id}/like")
//    public ResponseEntity<Boolean> boardLike(@PathVariable id, @RequestParam email) {
//        boolean result = boardService.toggleLike(id, email);
//        return ResponseEntity.ok(result);
//    }
}
