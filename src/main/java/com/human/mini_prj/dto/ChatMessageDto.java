package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChatMessageDto {
    // ENTER : 방 입장 (입장 메세지를 보내기 위해 구분)
    // TALK  : 메새지 작성해서 날림
    // CLOSE : 방 퇴장 (퇴장 메세지를 보내기 위해 구분)
    public enum MessageType { ENTER, TALK, CLOSE }
    private MessageType type;
    private String roomId;  // 방을 구분하기 위해 방 아이디 구현
    private String sender;  // 메새지를 보내는 사람
    private String message;
}
