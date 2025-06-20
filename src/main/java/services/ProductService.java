package services;

import domain.category.Category;
import domain.category.CategoryDto;
import domain.product.Product;
import domain.product.ProductDto;
import exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Product insert(ProductDto productDto) {
        Product product = new Product(productDto);

        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product update(String id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if(!productDto.title().isEmpty() || !productDto.title().trim().isBlank())
            product.setTitle(productDto.title());
        if(!productDto.description().isEmpty() || !productDto.description().trim().isBlank())
            product.setDescription(productDto.description());

        return productRepository.save(product);
    }

    public void delete(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        productRepository.delete(product);
    }
}
