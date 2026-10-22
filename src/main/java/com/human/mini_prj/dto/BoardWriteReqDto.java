package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardWriteReqDto {
    private String title;
    private String content;
    private String email;   // 작성자가 누구인지 구별하기 위해 포함
}
