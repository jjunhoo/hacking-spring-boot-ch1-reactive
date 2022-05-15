package com.grelturnquist.hackingspringboot.reactive.commerce.controller;

import com.grelturnquist.hackingspringboot.reactive.commerce.domain.Cart;
import com.grelturnquist.hackingspringboot.reactive.commerce.domain.CartItem;
import com.grelturnquist.hackingspringboot.reactive.commerce.repository.CartRepository;
import com.grelturnquist.hackingspringboot.reactive.commerce.repository.ItemRepository;
import com.grelturnquist.hackingspringboot.reactive.commerce.service.CartService;
import com.grelturnquist.hackingspringboot.reactive.commerce.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller // JSON/XML 과 같은 데이터가 아니라 템플릿을 사용한 웹 페이지를 반환하는 스프링 웹 컨트롤러를 나타내는 어노테이션
public class HomeController {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;
    private CartService cartService;
    private InventoryService inventoryService;

    // itemRepository, cartRepository 를 생성자를 통하여 주입 (생성자 주입 - Constructor Injection)
    public HomeController(ItemRepository itemRepository, CartRepository cartRepository, CartService cartService, InventoryService inventoryService) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.inventoryService = inventoryService;
    }

    @GetMapping // 아무 경로를 표시하지 않으면 '/' default
    Mono<Rendering> home() { // view/attribute 를 포함하는 웹플럭스 컨테이너인 Mono<Rendering> 반환
        return Mono.just(Rendering.view("home.html") // view 로 렌더링에 사용할 템플릿 이름 지정
            .modelAttribute("items",
                    this.itemRepository.findAll()) // modelAttribute 로 템플릿에서 사용될 데이터 지정
            .modelAttribute("cart",
                    this.cartRepository.findById("My Cart") // Mongo DB 에서 장바구니 조회 시 "My Cart"가 없으면 새로운 Cart 를 생성하여 반환
                        .defaultIfEmpty(new Cart("My Cart")))
            .build());
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@PathVariable String id) {
        return this.cartService.addToCart("My Cart", id)
                .thenReturn("redirect:/");
    }

    @GetMapping("/search")
    Mono<Rendering> search (
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam boolean useAnd) {
        return Mono.just(Rendering.view("home.html")
            .modelAttribute("items",
                    inventoryService.searchByExample(name, description, useAnd))
            .modelAttribute("cart",
                    this.cartRepository.findById("My Cart")
                        .defaultIfEmpty(new Cart("My Cart")))
            .build());
    }
}
