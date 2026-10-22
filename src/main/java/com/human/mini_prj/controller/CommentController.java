package com.human.mini_prj.controller;

import com.human.mini_prj.dto.CommentResDto;
import com.human.mini_prj.dto.CommentWriteReqDto;
import com.human.mini_prj.repository.CommentRepository;
import com.human.mini_prj.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment") // http://localhost:8111/comment
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    // 게시글별 전체 댓글 조회
    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<CommentResDto>> commentList(@PathVariable Long boardId) {
        List<CommentResDto> list = commentService.getCommentList(boardId);
        return ResponseEntity.ok(list);
    }

    // 댓글 작성
    @PostMapping("/write")
    public ResponseEntity<Boolean> commentWrite(@RequestBody CommentWriteReqDto dto) {
        boolean result = commentService.writeComment(dto);
        return ResponseEntity.ok(result);
    }

    // 댓글 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<Boolean> commentModify(@PathVariable Long id, @RequestBody CommentWriteReqDto dto) {
        boolean result = commentService.updateComment(id, dto);
        return ResponseEntity.ok(result);
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{id}")
    // @RequestParam(쿼리 파라미터/쿼리 스트링) : ?email=123@naver.com 처럼 옵션, 또는 필터를 걸어야 할때 사용
    public ResponseEntity<Boolean> commentDelete(@PathVariable Long id, @RequestParam String email) {
        boolean result = commentService.deleteComment(id, email);
        return ResponseEntity.ok(result);
    }
}
