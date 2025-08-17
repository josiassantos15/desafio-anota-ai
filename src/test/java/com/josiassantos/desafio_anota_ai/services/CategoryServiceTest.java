package com.josiassantos.desafio_anota_ai.services;

import com.josiassantos.desafio_anota_ai.AutoDisplayNameGenerator;
import com.josiassantos.desafio_anota_ai.commons.exceptions.ValidationException;
import com.josiassantos.desafio_anota_ai.domain.category.Category;
import com.josiassantos.desafio_anota_ai.domain.category.CategoryDto;
import com.josiassantos.desafio_anota_ai.repositories.CategoryRepository;
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
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AwsSnsService awsSnsService;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    void insert_ShouldSaveAndPublishEvent() {
        String id = "654";
        CategoryDto dto = eletronicsCategoryDto;
        Category savedCategory = new Category(dto);
        savedCategory.setId(id);

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        Category result = categoryService.insert(dto);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(id, result.getId()),
                () -> assertEquals(dto.title(), result.getTitle()),
                () -> verify(awsSnsService).pubish(any(MessageDto.class)),
                () -> verifyNoMoreInteractions(categoryRepository, awsSnsService)
        );
    }

    @Test
    void getById_ShouldReturnCategory() {
        String id = "123";
        Category category = eletronicsCategory;
        category.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getById(id);

        assertAll(
                () -> assertNotNull(result),
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(id, result.get().getId()),
                () -> verifyNoMoreInteractions(categoryRepository)
        );
    }

    @Test
    void getAll_ShouldReturnAllCategories() {
        Category cat1 = eletronicsCategory;
        cat1.setId("1");
        Category cat2 = sportsCategory;
        cat2.setId("2");

        when(categoryRepository.findAll()).thenReturn(List.of(cat1, cat2));

        List<Category> result = categoryService.getAll();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> verifyNoMoreInteractions(categoryRepository)
        );
    }

    @Test
    void update_ShouldUpdateExistingCategory() {
        String id = "123";
        CategoryDto updateDto = eletronicsCategoryDto;

        Category existing = new Category();
        existing.setId(id);
        existing.setTitle("Original");
        existing.setDescription("Desc Original");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        Category result = categoryService.update(id, updateDto);

        assertAll(
                () -> assertEquals("Eletrônicos", result.getTitle()),
                () -> assertEquals("Dispositivos eletrônicos", result.getDescription()),
                () -> verify(awsSnsService).pubish(any(MessageDto.class)),
                () -> verifyNoMoreInteractions(categoryRepository, awsSnsService)
        );
    }

    @Test
    void update_ShouldThrowWhenNotFound() {
        String id = "999";
        CategoryDto updateDto = eletronicsCategoryDto;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class,
                () -> categoryService.update(id, updateDto));

        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Update Category", exception.getFieldName()),
                () -> assertTrue(exception.getMessage().contains(id)),
                () -> verifyNoMoreInteractions(categoryRepository),
                () -> verifyNoInteractions(awsSnsService)
        );
    }

    @Test
    void delete_ShouldDeleteExisting() {
        String id = "123";
        Category existing = new Category();
        existing.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(existing));

        categoryService.delete(id);

        assertAll(
                () -> assertNotNull(categoryRepository.findById(id)),
                () -> verify(categoryRepository).delete(existing),
                () -> verifyNoMoreInteractions(categoryRepository)
        );
    }

    @Test
    void delete_ShouldThrowWhenNotFound() {
        String id = "999";
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> categoryService.delete(id));

        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Delete Category", exception.getFieldName()),
                () -> assertTrue(exception.getMessage().contains(id)),
                () -> verifyNoMoreInteractions(categoryRepository)
        );
    }
}
