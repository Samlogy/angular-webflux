package com.example.spring.webflux.unit;

import com.example.spring.webflux.product.Product;
import com.example.spring.webflux.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@DataMongoTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll().block();
    }

    @Test
    void saveProduct_shouldPersistProduct() {
        Product product = new Product(null, "Laptop", 999.99);

        StepVerifier.create(productRepository.save(product))
                .assertNext(savedProduct -> {
                    assertNotNull(savedProduct.getId());
                    assertEquals("Laptop", savedProduct.getName());
                })
                .verifyComplete();
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        Product product1 = new Product(null, "Laptop", 999.99);
        Product product2 = new Product(null, "Phone", 499.99);

        productRepository.saveAll(Flux.just(product1, product2)).blockLast();

        StepVerifier.create(productRepository.findAll())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findById_existingId_shouldReturnProduct() {
        Product product = new Product(null, "Laptop", 999.99);
        Product savedProduct = productRepository.save(product).block();

        StepVerifier.create(productRepository.findById(savedProduct.getId()))
                .expectNext(savedProduct)
                .verifyComplete();
    }

    @Test
    void deleteProduct_existingId_shouldRemoveProduct() {
        Product product = new Product(null, "Laptop", 999.99);
        Product savedProduct = productRepository.save(product).block();

        productRepository.delete(savedProduct).block();

        StepVerifier.create(productRepository.findById(savedProduct.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }
}
