package com.josiassantos.desafio_anota_ai.controllers;

import com.josiassantos.desafio_anota_ai.domain.product.Product;
import com.josiassantos.desafio_anota_ai.domain.product.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.josiassantos.desafio_anota_ai.services.ProductService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/product")
public class ProductCrontroller implements ProductOperations{
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDto productDto) {
        log.info("Inserting a product with title: {}", productDto.title());
        return ResponseEntity.ok(productService.insert(productDto));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        log.info("Getting all products");
        return ResponseEntity.ok(productService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id,
                                          @RequestBody ProductDto productDto) {
        log.info("Updating a product with id: {}", id);
        return ResponseEntity.ok(productService.update(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable String id) {
        log.info("Deleting a product with id: {}", id);
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
