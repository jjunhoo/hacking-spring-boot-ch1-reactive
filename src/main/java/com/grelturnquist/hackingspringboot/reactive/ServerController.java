package com.grelturnquist.hackingspringboot.reactive;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

// 화면 구성을 위한 템플릿을 사용하는 대신, 결과 데이터를 직렬화하고 HTTP 응답 본문에 직접 써서 반환하는 REST 컨트롤러
@RestController
public class ServerController {

    private final KitchenService kitchen;

    // 어플리케이션 구동시 스프링은 KitchenService 인스턴스를 찾아 자동으로 '생성자 주입'
    public ServerController(KitchenService kitchen) {
        this.kitchen = kitchen;
    }

    @GetMapping(value = "/server", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // 반환 미디어 타입 : text/event-stream
    Flux<Dish> serveDishes() {
        return this.kitchen.getDishes();
    }

    @GetMapping(value = "/served-dished", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Dish> deliverDished() {
        // getDishes()가 반환하는 Flux<Dish>를 deliver() 매핑 함수를 사용해서 변환한 후 반환
        return this.kitchen.getDishes()
                .map(dish -> Dish.deliver(dish));
    }
}
