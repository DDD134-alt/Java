package com.human.mini_prj.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

// 페이지네이션 공통 응답 DTO
// - 어떤 목록이든 이 DTO로 재사용 할 수 있도록 제네릭(T)으로 만듦
// - Page 객체를 그대로 반환하디 않고 프론트애 핑요한 정보만 추려서 내려줌

@Getter
public class PageResDto<T> {
    private final List<T> content;    // 현재 페이지의 데이터 목록
    private final int page;           // 현재 페이지 번호 (0부터 시작)
    private final int size;           // 한 페이지당 데이터 개수
    private final long totalElements; // 전체 데이터 개수
    private final int totalPages;     // 전체 페이지 수
    private final boolean first;      // 첫 페이지 여부, 첫 페이지일때 이전 버튼을 비활성화 할 수 있도록 알려주는 참거짓 값
    private final boolean last;       // 마지막 페이지 여부, 마지막 페이지일때 다음 버튼을 비활성화 할 수 있도록 알려주는 참거짓 값

    public PageResDto(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
    }
}
