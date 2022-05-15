package com.grelturnquist.hackingspringboot.reactive.commerce.repository;

import com.grelturnquist.hackingspringboot.reactive.commerce.domain.Item;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ItemRepository extends ReactiveCrudRepository<Item, String>, ReactiveQueryByExampleExecutor<Item> {
    // Item : Repository 가 저장하고 조회하는 타입
    // String : 저장되는 데이터의 식별자 타입이 String 이라는 것을 의미
    Flux<Item> findByNameContaining(String partialName); // 고객이 입력한 검색어가 이름에 포함된 상품을 반환하는 메소드
}
