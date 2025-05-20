package com.example.spring.webflux.unit;

import com.example.spring.webflux.product.Product;
import com.example.spring.webflux.product.ProductController;
import com.example.spring.webflux.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@WebFluxTest(ProductController.class)
@Import({ProductService.class})
public class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        Product product = new Product(null, "Laptop", 999.99);
        Product savedProduct = new Product(1, "Laptop", 999.99);

        when(productService.createProduct(product)).thenReturn(Mono.just(savedProduct));

        webTestClient.post()
                .uri("/api/products")
                .bodyValue(product)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Laptop");
    }

    @Test
    void getAllProducts_shouldReturnProductList() {
        Product product1 = new Product(1, "Laptop", 999.99);
        Product product2 = new Product(2, "Phone", 499.99);

        when(productService.getAllProducts()).thenReturn(Flux.just(product1, product2));

        webTestClient.get()
                .uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(2)
                .contains(product1, product2);
    }

    @Test
    void getProductById_existingId_shouldReturnProduct() {
        Product product = new Product(1, "Laptop", 999.99);

        when(productService.getProductById(1)).thenReturn(Mono.just(product));

        webTestClient.get()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Laptop");
    }

    @Test
    void updateProduct_existingId_shouldReturnUpdatedProduct() {
        Product updatedProduct = new Product(1, "Laptop Pro", 1299.99);

        when(productService.updateProduct(eq(1), any(Product.class))).thenReturn(Mono.just(updatedProduct));

        webTestClient.put()
                .uri("/api/products/1")
                .bodyValue(updatedProduct)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Laptop Pro");
    }

    @Test
    void deleteProduct_existingId_shouldReturnNoContent() {
        when(productService.deleteProduct(1)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}

