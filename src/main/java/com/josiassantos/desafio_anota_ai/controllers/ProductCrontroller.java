package com.josiassantos.desafio_anota_ai.controllers;

import com.josiassantos.desafio_anota_ai.domain.product.ProductDto;
import com.josiassantos.desafio_anota_ai.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/product")
public class ProductCrontroller implements ProductOperations{
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> insert(@RequestBody ProductDto productDto) {
        log.info("Inserting a product with title: {}", productDto.title());
        return ResponseEntity.ok(new ProductDto(productService.insert(productDto)));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        log.info("Getting all products");
        return ResponseEntity.ok(productService.getAll()
                .stream()
                .map(ProductDto::new)
                .toList()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable String id,
                                          @RequestBody ProductDto productDto) {
        log.info("Updating a product with id: {}", id);
        return ResponseEntity.ok(new ProductDto(productService.update(id, productDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting a product with id: {}", id);
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
