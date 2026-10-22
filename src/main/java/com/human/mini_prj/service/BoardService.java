package com.human.mini_prj.service;

import com.human.mini_prj.dto.BoardLikeResDto;
import com.human.mini_prj.dto.BoardResDto;
import com.human.mini_prj.dto.BoardWriteReqDto;
import com.human.mini_prj.dto.PageResDto;
import com.human.mini_prj.entity.Board;
import com.human.mini_prj.entity.BoardLike;
import com.human.mini_prj.entity.Member;
import com.human.mini_prj.repository.BoardRepository;
import com.human.mini_prj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 게시글 전체 조회 (최신순)
    public List<BoardResDto> getBoardList() {
        List<Board> boardList = boardRepository.findAllByOrderByRegTimeDesc();
        List<BoardResDto> boardResDtoList = new ArrayList<>();
        for (Board board : boardList) {
            boardResDtoList.add(toDto(board));
        }
        return boardResDtoList;
    }

    // 게시글 상세 조회
    public BoardResDto getBoardDetail(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }
        return toDto(board.get());
    }

    // 게시글 작성
    public boolean writeBoard(BoardWriteReqDto dto) {
        try {
            // 작성자 조회
            Optional<Member> member = memberRepository.findByEmail(dto.getEmail());
            if (member.isEmpty()) {
                throw new IllegalArgumentException("해당 회원이 존재하지 않습니다: " + dto.getEmail());
            }
            Board board = new Board();
            board.setTitle(dto.getTitle());
            board.setContent(dto.getContent());
            board.setMember(member.get());
            boardRepository.save(board); // INSERT 와 동일
            return true;

        } catch (Exception e) {
            log.error("게시글 작성 시 오류 발생 : {}", e.getMessage());
            return false;
        }
    }

    // 게시글 수정
    public boolean updateBoard(Long id, BoardWriteReqDto dto) {
        try {
            // 1. 수정할 게시글이 실제로 존재하는지 조회
            Optional<Board> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isEmpty()) {
                throw new IllegalArgumentException(id + "번 게시글이 존재하지 않습니다.");
            }
            Board board = optionalBoard.get();

            // 2. 작성자 본인 확인 (프론트엔드에서 전달받은 이메일과 기존 게시글 작성자 이메일 비교)
            Member member = board.getMember();
            String email = member.getEmail();
            if (!email.equals(dto.getEmail())) {
                throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
            }

            // 3. 제목과 내용 변경 후 저장
            board.setTitle(dto.getTitle());
            board.setContent(dto.getContent());
            return true;

        } catch (Exception e) {
            log.error("게시글 수정 시 오류 발생 : {}", e.getMessage());
            return false;
        }
    }

    // 게시글 삭제
    public boolean deleteBoard(Long id) {
        try {
            Optional<Board> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isEmpty()) {
                throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
            }

            Board board = optionalBoard.get();
            // 삭제는 날려줘야 함
            boardRepository.delete(board);
            return true;

        } catch (Exception e) {
            log.error("게시글 삭제 시 오류 발생 : {}", e.getMessage());
            return false;
        }
    }

    // 게시글 목록 페이지 페이지네이션
    public PageResDto<BoardResDto> getBoardPage(int page, int size) {
        // 1. 페이징 및 정렬 조건 객체 생성
        // - PageRequest.of : 페이지번호, 페이지크기, 정렬, 참고로 페이지번호는 0부터 시작 (0 = 첫 페이지)
        // - Sort.by("엔티티 필드명")  : 정렬방향, 괄호 안에는 DB 컬럼명이 아니라 '엔티티 필드명'을 씀
        Pageable pageable = PageRequest.of(page, size, Sort.by("regTime").descending());
        // 2. DB에서 페이징이 적용된 회원 엔티티 목록 조회
        Page<Board> boardPage = boardRepository.findAll(pageable);
        // 3. 조회한 엔티티(Entity)를 응답용 DTO로 변환
        Page<BoardResDto> boardResDtoPage = boardPage.map(this::toDto);
        // 4. 공통 페이지 응답 DTO로 감싸서 반환
        return new PageResDto<>(boardResDtoPage);
    }

    // 게시글 좋아요
    public boolean toggleLike(Long boardId, String email) {
        // 1.회원이 실제로 존재하는지 엔티티 조회
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("회원이 존재하지 않습니다");
        }

        // 2.게시글이 실제로 존재하는지 엔티티 조회
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isEmpty()) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다");
        }

        // 3.해당 회원이 이 게시글에 이미 좋아요를 눌렀는지 확인
        Optional<BoardLike> existingLike = BoardRepository.findByBoard_IdAndMember_Email(boardId, memberEmail);

        if (existingLike.isPresent()) {
            // 3-1. 이미 좋아요를 누른 상태라면 -> 좋아요 취소 (데이터 삭제)
            BoardRepository.delete(existingLike.get());
            return false; // 좋아요 취소됨을 알림
        } else {
            // 3-2. 아직 좋아요를 안 누른 상태라면 -> 좋아요 추가 (데이터 저장)
            BoardLike boardLike = new BoardLike();
            boardLike.setBoard(board);
            boardLike.setMember(member);

            BoardRepository.save(boardLike);
            return true; // 좋아요가 추가됨을 알림
        }
    }

    // Entity -> Dto
    private BoardResDto toDto(Board board) {
        BoardResDto dto = new BoardResDto();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setEmail(board.getMember().getEmail());
        dto.setName(board.getMember().getName());
        dto.setRegDate(board.getRegTime());
        return dto;
    }
}
