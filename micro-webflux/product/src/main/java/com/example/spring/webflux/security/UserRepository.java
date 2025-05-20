package com.example.spring.webflux.security;

import com.example.spring.webflux.security.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, Integer> {
    Mono<User> findByUsername(String username);
}
