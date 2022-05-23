package com.grelturnquist.hackingspringboot.reactive.commerce.component;

import com.grelturnquist.hackingspringboot.reactive.commerce.domain.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

// MongoOperations 기반 데이터베이스 로더
@Component
public class TemplateDatabaseLoader {
    @Bean
    CommandLineRunner initialize(MongoOperations mongo) { // 블로킹 버전의 MongoTemplate 를 사용하여 데이터 적재
        return args -> {
            mongo.save(new Item("Alf alarm clock", "Clock",19.99));
            mongo.save(new Item("Smurf TV tray", "TV",24.99));
        };
    }
}
