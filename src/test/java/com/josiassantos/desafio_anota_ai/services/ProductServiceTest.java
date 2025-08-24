package com.josiassantos.desafio_anota_ai.services;

import com.josiassantos.desafio_anota_ai.AutoDisplayNameGenerator;
import com.josiassantos.desafio_anota_ai.commons.exceptions.ValidationException;
import com.josiassantos.desafio_anota_ai.domain.category.Category;
import com.josiassantos.desafio_anota_ai.domain.product.Product;
import com.josiassantos.desafio_anota_ai.domain.product.ProductDto;
import com.josiassantos.desafio_anota_ai.repositories.ProductRepository;
import com.josiassantos.desafio_anota_ai.services.aws.AwsSnsService;
import com.josiassantos.desafio_anota_ai.services.aws.MessageDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.josiassantos.desafio_anota_ai.FactoryUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(AutoDisplayNameGenerator.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private AwsSnsService awsSnsService;
    @InjectMocks
    private ProductService productService;

    @Test
    void insert_shouldSaveAndPublishEvent() {
        String id = "123";
        ProductDto productDto = eletronicProductDto;
        Category category = eletronicsCategory;
        Product savedProduct = new Product(productDto);
        savedProduct.setId(id);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(categoryService.getById(anyString())).thenReturn(Optional.ofNullable(category));

        Product result = productService.insert(productDto);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(id, result.getId()),
                () -> assertEquals(productDto.title(), result.getTitle()),
                () -> verify(awsSnsService, times(1)).pubish(any(MessageDto.class)),
                () -> verify(categoryService, times(1)).getById(anyString()),
                () -> verifyNoMoreInteractions(productRepository, categoryService, awsSnsService)
        );
    }

    @Test
    void getAll_ShouldReturnAllProducts() {
        Product product1 = eletronicProduct;
        product1.setId("1");
        Product product2 = sportProduct;
        product2.setId("2");

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<Product> result = productService.getAll();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> verify(productRepository, times(1)).findAll(),
                () -> verifyNoMoreInteractions(productRepository)
        );
    }

    @Test
    void update_ShouldUpdateExistingProduct() {
        String id = "123";
        ProductDto updateDto = eletronicProductDto;
        Category category = eletronicsCategory;
        Product existing = new Product();
        existing.setId(id);
        existing.setTitle("Original");
        existing.setDescription("Desc Original");

        when(productRepository.findById(id)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        when(categoryService.getById(anyString())).thenReturn(Optional.ofNullable(category));

        Product result = productService.update(id, updateDto);

        assertAll(
                () -> assertEquals("TV 70", result.getTitle()),
                () -> assertEquals("Televisor de 50 polegadas XPTO", result.getDescription()),
                () -> verify(productRepository, times(1)).findById(anyString()),
                () -> verify(productRepository, times(1)).save(any(Product.class)),
                () -> verify(awsSnsService, times(1)).pubish(any(MessageDto.class)),
                () -> verifyNoMoreInteractions(productRepository, awsSnsService)
        );
    }

    @Test
    void update_ShouldThrowWhenNotFound() {
        String id = "999";
        ProductDto updateDto = eletronicProductDto;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class,
                () -> productService.update(id, updateDto));

        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Update Product", exception.getFieldName()),
                () -> assertTrue(exception.getMessage().contains(id)),
                () -> verify(productRepository, times(1)).findById(anyString()),
                () -> verifyNoMoreInteractions(productRepository),
                () -> verifyNoInteractions(awsSnsService)
        );
    }

    @Test
    void delete_ShouldDeleteExisting() {
        String id = "123";
        Product existing = new Product();
        existing.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(existing));

        productService.delete(id);

        assertAll(
                () -> assertNotNull(productRepository.findById(id)),
                () -> verify(productRepository, times(1)).delete(existing),
                () -> verifyNoMoreInteractions(productRepository)
        );
    }

    @Test
    void delete_ShouldThrowWhenNotFound() {
        String id = "999";
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> productService.delete(id));

        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Delete Product", exception.getFieldName()),
                () -> assertTrue(exception.getMessage().contains(id)),
                () -> assertEquals("Delete Product", exception.getFieldName()),
                () -> verifyNoMoreInteractions(productRepository)
        );
    }
}