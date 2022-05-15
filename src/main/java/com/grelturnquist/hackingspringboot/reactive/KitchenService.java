package com.grelturnquist.hackingspringboot.reactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service // 스프링 빈임을 나타내는 어노테이션, 컴포넌트 탐색 과정에서 스프링 부트에 의해 자동으로 생성되어 빈으로 등록
public class KitchenService {

    /**
     * 요리 스트림 생성
     * 3가지 요리 중에서 무작위로 선택도니 1개의 요리를 250ms 간격으로 제공
     */
    Flux<Dish> getDishes() {
        return Flux.<Dish> generate(sink -> sink.next(randomDish()))
                .delayElements(Duration.ofMillis(250));
    }

    /**
     * 요리 무작위 선택
     */
    private Dish randomDish() {
        return menu.get(picker.nextInt(menu.size()));
    }

    private List<Dish> menu = Arrays.asList(
            new Dish("Sesame chicken"),
            new Dish("Lo mein noodles, plain"),
            new Dish("Sweet & sour beef"));

    private Random picker = new Random();
}
