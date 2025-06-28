package com.josiassantos.desafio_anota_ai.services;

import com.josiassantos.desafio_anota_ai.commons.exceptions.ValidationException;
import com.josiassantos.desafio_anota_ai.domain.product.Product;
import com.josiassantos.desafio_anota_ai.domain.product.ProductDto;
import com.josiassantos.desafio_anota_ai.repositories.ProductRepository;
import com.josiassantos.desafio_anota_ai.services.aws.AwsSnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.josiassantos.desafio_anota_ai.services.aws.MessageDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final AwsSnsService awsSnsService;

    public Product insert(ProductDto productDto) {
        categoryService.getById(productDto.categoryId())
                .orElseThrow(() -> new ValidationException("Insert Product", "Category with id %s not found"
                        .formatted(productDto.categoryId())));
        Product product = new Product(productDto);
        Product productSalved = productRepository.save(product);
        awsSnsService.pubish(new MessageDto(product.toString()));

        return productSalved;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product update(String id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Update Product", "Product with id %s not found".formatted(id)));
        if (productDto.categoryId() != null) {
            categoryService.getById(productDto.categoryId())
                    .orElseThrow(() -> new ValidationException("Update Product", "Category with id %s not found"
                            .formatted(productDto.categoryId())));
            product.setCategory(productDto.categoryId());
        }
        if (!productDto.title().isEmpty() || !productDto.title().trim().isBlank())
            product.setTitle(productDto.title());
        if (!productDto.description().isEmpty() || !productDto.description().trim().isBlank())
            product.setDescription(productDto.description());
        if (productDto.price() != null)
            product.setPrice(productDto.price());

        Product productSalved = productRepository.save(product);
        awsSnsService.pubish(new MessageDto(productSalved.toString()));

        return productSalved;
    }

    public void delete(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Delete Product", "Product with id %s not found".formatted(id)));

        productRepository.delete(product);
    }
}
