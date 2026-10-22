package com.human.mini_prj.repository;

import com.human.mini_prj.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 기본적인 CRUD는 상속을 통해서 구현되어 있고, 이를 구현하는 구현체는 별도의 hibernate가 SQL 문으로 변경해줌
    // SQL : SELECT * FROM member WHERE email = ? 랑 같은 뜻
    Optional<Member> findByEmail(String email);

    // null이 떠버려도 에러가 나지 않도록 Optional으로 래핑
    // SQL ; SELECT count(*) FROM member WHERE email = ? 랑 같은 뜻
    boolean existsByEmail(String email);

    // exists + Email : Email 요거는 member에 있는 목록을 기입해야하고 카멜 표기법에 맞춰 작성해야 한다. 안그럼 애러남
    // SQL : SELECT * FROM member WHERE email = ? and pwd = ? 랑 같은 뜻
    // 비밀번호가 일체하는지 묻는 문
    Optional<Member> findByEmailAndPwd(String email, String pwd);

    // 1. 이름이 정확히 일치하는 회원 목록 조회
    // SQL : SELECT * FROM member WHERE name = ?
    List<Member> findByName(String name);

    // 2. 이름에 특정 글자가 포함된 회원 조회
    // SQL : SELECT * FROM member WHERE name LIKE '%?%'
    List<Member> findByNameContaining(String name);

    // 3. 특정 도메인(예 : @naver.com)으로 끝나는 이메일을 가진 회원 조회
    // SQL : SELECT * FROME member WHERE email LIKE '%@hu.com'
    List<Member> findByEmailEndingWith(String Email);

    // 4. 이름 또는 이메일이 일치하는 회원 조회
    // SQL : SELECT * FROM member WHERE name = ? or email = ?
    List<Member> findByNameOrEmail(String name, String email);

    // 5. 특정 일시 이후에 가입한 회원 조회
    // SQL : SELECT * FROM member WHERE regDate > ?
    List<Member> findByRegDateAfter(LocalDateTime regDate);

    // 6. 두 일시 사이에 가입한 회원 조회
    // SQL : SELECT * FROM member WHERE regDate BETWEEN ? AND ?
    List<Member> findByRegDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 7. 전체 회원을 최신 가입일 순으로 조회
    // SQL : SELECT * FROM member ORDER BY reg_date DESC
    List<Member> findAllByOrderByRegDateDesc();

    // 8. 가장 최근에 가입한 회원 3명 조회
    // SQL : SELECT * FROM member ORDER BY regDate DESC LIMIT 3
    List<Member> findTop3ByOrderByRegDateDesc();

    // 9. 대소문자 구분없이 이름에 키워드가 포함된 회원을 이름 오름차순으로 조회
    // SQL : SELECT * FROM member WHERE name LIKE '%?%' ORDER BY name ASC
    List<Member> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

    // 10. 이메일로 회원 삭제
    // SQL : DELETE FROM member WHERE email = ?
    void deleteByEmail(String email);
}
