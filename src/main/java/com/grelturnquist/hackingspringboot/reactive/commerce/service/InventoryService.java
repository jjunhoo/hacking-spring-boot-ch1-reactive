package com.grelturnquist.hackingspringboot.reactive.commerce.service;

import com.grelturnquist.hackingspringboot.reactive.commerce.domain.Item;
import com.grelturnquist.hackingspringboot.reactive.commerce.repository.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class InventoryService {

    private ItemRepository itemRepository;

    public InventoryService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Flux<Item> searchByExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd // 사용자가 선택한 useAnd 값에 따라 3항 연산자를 사용하여 분기해서 ExampleMatcher 생성
                ? ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // StringMatcher.CONTAINING 를 사용하여 부분 일치 검색 수행
                .withIgnoreCase() // 대소문자 미구분
                .withIgnorePaths("price"); // 'price' 필드 무시

        Example<Item> probe = Example.of(item, matcher); // Example 생성

        return itemRepository.findAll(probe); // 쿼리 실행
    }
}
