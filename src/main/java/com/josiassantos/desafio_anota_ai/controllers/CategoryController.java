package com.josiassantos.desafio_anota_ai.controllers;

import com.josiassantos.desafio_anota_ai.domain.category.Category;
import com.josiassantos.desafio_anota_ai.domain.category.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.josiassantos.desafio_anota_ai.services.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
public class CategoryController implements CategoryOperations {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryDto categoryDto) {
        log.info("Inserting category with title: {}", categoryDto.title());
        return ResponseEntity.ok(categoryService.insert(categoryDto));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        log.info("Getting all categories");
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable String id,
                                           @RequestBody CategoryDto categoryDto) {
        log.info("Updating category with id: {}", id);
        return ResponseEntity.ok(categoryService.update(id, categoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting category with id: {}", id);
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
