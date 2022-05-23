package com.grelturnquist.hackingspringboot.reactive.commerce.service;

import com.grelturnquist.hackingspringboot.reactive.commerce.domain.Cart;
import com.grelturnquist.hackingspringboot.reactive.commerce.domain.CartItem;
import com.grelturnquist.hackingspringboot.reactive.commerce.repository.CartRepository;
import com.grelturnquist.hackingspringboot.reactive.commerce.repository.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Mono<Cart> addToCart(String cartId, String id) {
        return this.cartRepository.findById(cartId)
                .log("foundCart")
                .defaultIfEmpty(new Cart(cartId))
                .log("emptyCart")
                .flatMap(cart -> cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getItem()
                            .getId().equals(id))
                    .findAny()
                    .map(cartItem -> {
                        cartItem.increment();
                        return Mono.just(cart);
                    })
                    .orElseGet(() ->
                        this.itemRepository.findById(id)
                                .log("fetchedItem")
                                .map(CartItem::new)
                                .log("cartItem")
                                .doOnNext(cartItem ->
                                        cart.getCartItems().add(cartItem))
                                .map(cartItem -> cart)))
                .log("cartWithAnotherItem")
                .flatMap(this.cartRepository::save)
                .log("savedCart");
    }
}
