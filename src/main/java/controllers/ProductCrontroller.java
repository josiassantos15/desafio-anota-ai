package controllers;

import domain.category.Category;
import domain.category.CategoryDto;
import domain.product.Product;
import domain.product.ProductDto;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/product")
public class ProductCrontroller {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.insert(productDto));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathParam("id") String id,
                                           @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.update(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathParam("id") String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
