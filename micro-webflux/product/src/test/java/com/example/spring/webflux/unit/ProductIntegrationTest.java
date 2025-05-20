package com.example.spring.webflux.unit;

import com.example.spring.webflux.product.Product;
import com.example.spring.webflux.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@SpringBootTest
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll().block();

        Flux.range(1, 5)
                .map(i -> new Product(i, "Product " + i, 10.0 * i))
                .collectList()
                .flatMapMany(productRepository::saveAll)
                .blockLast();
    }

    @Test
    void testGetAllProducts() {
        webTestClient.get().uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(5);
    }

    @Test
    void testCreateProduct() {
        Product newProduct = new Product(null, "Tablet", 299.0);

        webTestClient.post().uri("/api/products")
                .bodyValue(newProduct)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Tablet");
    }
}
