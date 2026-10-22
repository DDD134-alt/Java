package com.human.mini_prj.repository;

import com.human.mini_prj.constant.ItemSellStatus;
import com.human.mini_prj.entity.Item;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest // 스프링 부트 환경을 로드하여 테스트를 수행
@Transactional  // 테스트가 끝난 후 데이터베이스 변경 사항을 원래대로 롤백(Rollback, 취소 후 되돌림), 근데 없어도 괜찮다고 함

class ItemRepositoryTest {

    @Autowired // 필드를 통한 의존성 주입(Dependency Injection, 필요한 객체를 스프링이 자동으로 연결해 주는 기능)
    private ItemRepository itemRepository;

    // 기본적으로 있어야 하는 거
    @BeforeEach
    public void createItemTest() {
        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(1000 * i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item saveItem = itemRepository.save(item);
            log.info("Item 저장 : {}", saveItem);
        }
    }

    @Test
    @DisplayName("상품 조회 테스트")
    public void findByItemNmTest() {
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품5");
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("1.상품 검색 테스트") // 테스트 실행 시 콘솔에 표시될 이름
    void findByItemNmOrItemDetailTest() {
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("상품 이름1", "상품 디테일1");
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("2.가격 LessThan 테스트")
    void findByPriceLessThanTest() {
        List<Item> itemList = itemRepository.findByPriceLessThan(10000);
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("3.가격 LessThanOrderBy 테스트")
    void findByPriceLessThanOrderByPriceDescTest() {
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10000);
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("4.가격 범위 검색 테스트")
    void findByPriceBetweenTest() {
        List<Item> itemList = itemRepository.findByPriceBetween(1000, 2000);
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("5.특정문자 상품명 검색 테스트")
    void findByItemNmContainingTest() {
        List<Item> itemList = itemRepository.findByItemNmContaining("특정문자");
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("6.판매상태 검색 테스트")
    void findByItemSellStatusTest() {
        List<Item> itemList = itemRepository.findByItemSellStatus(ItemSellStatus.SELL);
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("7.판매상태와 특정값 검색 테스트")
    void findByItemSellStatusAndPriceGreaterThanEqualTest() {
        List<Item> itemList = itemRepository.findByItemSellStatusAndPriceGreaterThanEqual(ItemSellStatus.SELL, 1000);
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("8.가격 Top3 상품 검색 테스트")
    void findTop3ByOrderByPriceDescTest() {
        List<Item> itemList = itemRepository.findTop3ByOrderByPriceDesc();
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("9.가격 목록 중 상품 검색 테스트")
    void findByPriceInTest() {
        List<Integer> priceList = List.of(1000, 2000, 3000);
        List<Item> itemList = itemRepository.findByPriceIn(priceList);
        for(Item item : itemList) {
            log.info("상품 조회 테스트 : {}", item);
        }
    }

    @Test
    @DisplayName("10.판매 상태별 상품 개수 조회 테스트")
    void countByItemSellStatusTest() {
        // 1. 개수를 세어주는 메서드를 호출하고, 결과를 Long 타입 변수에 담습니다.
        Long count = itemRepository.countByItemSellStatus(ItemSellStatus.SELL);
        // 2. 결과를 로그로 출력해서 확인합니다.
        log.info("판매 중인 상품 개수 : {}", count);
    }
}