package services;

import domain.category.Category;
import domain.category.exceptions.CategoryNotFoundException;
import domain.product.Product;
import domain.product.ProductDto;
import domain.product.exceptions.ProductNotfoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product insert(ProductDto productDto) {
        Category category = categoryService.getById(productDto.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product product = new Product(productDto);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product update(String id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotfoundException::new);
        categoryService.getById(productDto.categoryId())
                .ifPresent(product::setCategory);

        if (!productDto.title().isEmpty() || !productDto.title().trim().isBlank())
            product.setTitle(productDto.title());
        if (!productDto.description().isEmpty() || !productDto.description().trim().isBlank())
            product.setDescription(productDto.description());
        if (productDto.price() != null)
            product.setPrice(productDto.price());

        return productRepository.save(product);
    }

    public void delete(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        productRepository.delete(product);
    }
}
