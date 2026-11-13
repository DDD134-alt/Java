package com.human.mini_prj.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ChatRoomResDto {
    private final String roomId;
    private final String name;
    private final LocalDateTime regDate;

    @JsonIgnore // 직렬화 방지용 : 내부에서만 사용하겠다
    // Set : 중복 방지
    // ConcurrentHashMap : 멀티쓰레드 환경보장 (동시성 이슈를 방지함)
    // 하나의 채팅방에 여러 사용자가 동시에 입장하거나 퇴장할 수 있음
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Builder
    public ChatRoomResDto(String roomId, String name, LocalDateTime regDate) {
        this.roomId = roomId;
        this.name = name;
        this.regDate = regDate;
    }

    @JsonIgnore
    public boolean isSessionEmpty() {
        return sessions.isEmpty();
    }
}
