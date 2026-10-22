package com.human.mini_prj.service;

import com.human.mini_prj.dto.CommentResDto;
import com.human.mini_prj.dto.CommentWriteReqDto;
import com.human.mini_prj.entity.Board;
import com.human.mini_prj.entity.Comment;
import com.human.mini_prj.entity.Member;
import com.human.mini_prj.repository.BoardRepository;
import com.human.mini_prj.repository.CommentRepository;
import com.human.mini_prj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    // 게시글별 전체 댓글 조회
    public List<CommentResDto> getCommentList(Long boardId) {
        List<Comment> commentList = commentRepository.findByBoardIdOrderByRegDateDesc(boardId);
        List<CommentResDto> commentResDtos = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentResDto resDto = toDto(comment);
            commentResDtos.add(resDto);
        }
        return commentResDtos;
    }

    // 댓글 작성
    public boolean writeComment(CommentWriteReqDto dto) {
        try {
            // 1. 게시글이 실제로 존재하는지 조회해서 optionalBoard에 담기
            Optional<Board> optionalBoard = boardRepository.findById(dto.getBoardId());
            if (optionalBoard.isEmpty()) {
                throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다: " + dto.getBoardId());
            }

            // 💡 2. 여기서 board 변수를 선언하고 꺼내와야 합니다!
            Board board = optionalBoard.get();

            // 3. 댓글 객체 생성 후 값 채우기
            Comment comment = new Comment();
            comment.setEmail(dto.getEmail());
            comment.setContent(dto.getContent());
            comment.setRegDate(LocalDateTime.now());

            // 4. 위에서 꺼낸 board 객체를 통째로 세팅해주기 (setBoard 사용)
            comment.setBoard(board);

            commentRepository.save(comment); // DB에 저장 (INSERT)
            return true;

        } catch (Exception e) {
            log.error("댓글 작성 시 오류 발생 : {}", e.getMessage());
            return false;
        }
    }

    // 댓글 수정
    public boolean updateComment(Long id, CommentWriteReqDto dto) {
        try {
            // 1. 수정할 댓글이 실제로 존재하는지 조회
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (optionalComment.isEmpty()) {
                throw new IllegalArgumentException("해당 댓글이 존재하지 않습니다: " + dto.getBoardId());
            }
            Comment comment = optionalComment.get();

            if (!comment.getEmail().equals(dto.getEmail())) {
                throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
            }

            // 3. 제목과 내용 변경 후 저장
            comment.setContent(dto.getContent());
            return true;

        } catch (Exception e) {
            log.error("댓글 수정 시 오류 발생 : {}", e.getMessage());
            return false;
        }
    }


    // 댓글 삭제
    public boolean deleteComment(Long id, String email) {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (optionalComment.isEmpty()) {
                throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
            }
            Comment comment = optionalComment.get();

            if (!comment.getEmail().equals(email)) {
                throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
            }
            // 삭제는 날려줘야 함
            commentRepository.delete(comment);
            return true;

        } catch (Exception e) {
            log.error("댓글 삭제 시 오류 발생 : {}", e.getMessage());
            return false;
        }
    }


    // Entity > DTO
    private CommentResDto toDto(Comment comment) {
        CommentResDto dto = new CommentResDto();
        dto.setCommentId(comment.getCommentId());
        dto.setEmail(comment.getEmail());
        dto.setContent(comment.getContent());
        dto.setRegDate(comment.getRegDate());
        return dto;
    }
}
