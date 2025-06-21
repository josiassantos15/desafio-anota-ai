package controllers;

import domain.category.Category;
import domain.category.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.insert(categoryDto));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable() String id,
                                           @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.update(id, categoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable() String id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
