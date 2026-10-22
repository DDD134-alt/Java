package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeResDto {
    private Long id;
    private Long member_id;
    private Long board_id;
    private LocalDateTime regDate;
}
