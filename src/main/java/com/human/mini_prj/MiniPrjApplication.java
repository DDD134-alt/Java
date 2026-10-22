package com.human.mini_prj; // 이 파일이 속한 패키지 폴더 위치를 나타냅니다.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiniPrjApplication {

	public static void main(String[] args) {
		// 스프링 부트 애플리케이션을 실행(서버 가동)시키는 구문
		SpringApplication.run(MiniPrjApplication.class, args);
	}

}
