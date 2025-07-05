package com.josiassantos.desafio_anota_ai.controllers;

import com.josiassantos.desafio_anota_ai.domain.category.CategoryDto;
import com.josiassantos.desafio_anota_ai.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
public class CategoryController implements CategoryOperations {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> insert(@RequestBody CategoryDto categoryDto) {
        log.info("Inserting category with title: {}", categoryDto.title());
        return ResponseEntity.ok(new CategoryDto(categoryService.insert(categoryDto)));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        log.info("Getting all categories");
        return ResponseEntity.ok(categoryService.getAll()
                .stream()
                .map(CategoryDto::new)
                .toList()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable String id,
                                           @RequestBody CategoryDto categoryDto) {
        log.info("Updating category with id: {}", id);
        return ResponseEntity.ok(new CategoryDto(categoryService.update(id, categoryDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting category with id: {}", id);
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
