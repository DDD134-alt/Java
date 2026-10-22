package com.human.mini_prj.exception;

// 에러 코드 모음
// - 생성의의 : 에러 메시지를 서비스 곳곳에 흩뿌리지 않고 한 곳에서 관리하기 위해 패키지로 구성
// - HTTP 상태 코드 + 프론트에서 구분할 코드 + 사용자에게 보여줄 메시지를 한 묶음으로 정의
// - 새로운 에러가 필요하면 여기에 한 줄만 추가하면 되는 편의성!

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 모든 컨트롤러에 공통으로 적용되는 예외처리 담당 클래스
public class GlobalExceptionHandler {
    // @ExceptionHandler(엑셉션핸들러) : 괄호 안에 오류가 생기면 담당 메서드가 뺑이처라 클래스

    // - Miss~.Class : 클라이언트(프론트엔드 화면)가 서버로 요청을 보낼 때,
    //   필수적으로 보내야 하는 파라미터(외부에서 전달되는 데이터 값) 누락시 자동으로 던지는 예외
    // - 응답 : 400 (Bad Request, 잘못된 요청) "MISSING_PARAM"
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        String errorMessage = ex.getParameterName();
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("MISSING_PARAM", errorMessage + "파라미터가 누락되었습니다."));
    }

    // - Data~.class : 데이터베이스(데이터를 영구적으로 저장하는 창고)와
    //   통신하는 과정 중 문제가 생겼을 때 스프링의 데이터 액세스 계층이 던지는 예외
    // - 응답 : 500 (Internal Server Error, 서버 내부 오류) "DB_ERROR"
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDBError() {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse("DB_ERROR", "데이터베이스 오류가 발생했습니다."));
    }

    // - CustomException.class : CustomException이 서비스 로직 도중 throw 될 때 작동
    // - 응답 : CustomException.java에 있는 메세지가 뜬다
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustom(CustomException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    // - Exception.class : 위 코드에서 미리 잡지 못한 모든 종류의 에러 발생시 던지는 예외
    // - 응답 : 500 (Internal Server Error, 서버 내부 오류) "UNKNOWN"
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleDefault(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse("UNKNOWN", "알 수 없는 서버 오류"));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("잘못된 요청: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)   // 404
                .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
    }
}
