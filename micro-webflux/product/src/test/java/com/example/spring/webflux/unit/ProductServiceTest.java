package com.example.spring.webflux.unit;

import com.example.spring.webflux.product.Product;
import com.example.spring.webflux.product.ProductController;
import com.example.spring.webflux.product.ProductRepository;
import com.example.spring.webflux.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldReturnSavedProduct() {
        Product product = new Product(null, "Laptop", 999.99);
        Product savedProduct = new Product(1, "Laptop", 999.99);

        when(productRepository.save(product)).thenReturn(Mono.just(savedProduct));

        StepVerifier.create(productService.createProduct(product))
                .expectNext(savedProduct)
                .verifyComplete();
    }

    @Test
    void getAllProducts_shouldReturnProductList() {
        Product product1 = new Product(1, "Laptop", 999.99);
        Product product2 = new Product(2, "Phone", 499.99);

        when(productRepository.findAll()).thenReturn(Flux.just(product1, product2));

        StepVerifier.create(productService.getAllProducts())
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }

    @Test
    void getProductById_existingId_shouldReturnProduct() {
        Product product = new Product(1, "Laptop", 999.99);

        when(productRepository.findById(1)).thenReturn(Mono.just(product));

        StepVerifier.create(productService.getProductById(1))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void getProductById_nonExistingId_shouldReturnError() {
        when(productRepository.findById(1)).thenReturn(Mono.empty());

        StepVerifier.create(productService.getProductById(1))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void updateProduct_existingId_shouldReturnUpdatedProduct() {
        Product existingProduct = new Product(1, "Laptop", 999.99);
        Product updatedProduct = new Product(1, "Laptop Pro", 1299.99);

        when(productRepository.findById(1)).thenReturn(Mono.just(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(productService.updateProduct(1, updatedProduct))
                .expectNext(updatedProduct)
                .verifyComplete();
    }

    @Test
    void deleteProduct_existingId_shouldComplete() {
        Product product = new Product(1, "Laptop", 999.99);

        when(productRepository.findById(1)).thenReturn(Mono.just(product));
        when(productRepository.delete(product)).thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(1))
                .verifyComplete();
    }
}

