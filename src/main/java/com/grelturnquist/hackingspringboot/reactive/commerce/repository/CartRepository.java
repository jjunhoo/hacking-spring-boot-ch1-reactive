package com.grelturnquist.hackingspringboot.reactive.commerce.repository;

import com.grelturnquist.hackingspringboot.reactive.commerce.domain.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

// Cart 객체 관리를 위한 리액티브 Repository 정의
public interface CartRepository extends ReactiveCrudRepository<Cart, String> {
}
