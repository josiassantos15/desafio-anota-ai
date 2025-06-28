package com.josiassantos.desafio_anota_ai.services;

import com.josiassantos.desafio_anota_ai.domain.category.Category;
import com.josiassantos.desafio_anota_ai.domain.category.CategoryDto;
import com.josiassantos.desafio_anota_ai.repositories.CategoryRepository;
import com.josiassantos.desafio_anota_ai.domain.category.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.josiassantos.desafio_anota_ai.services.aws.AwsSnsService;
import com.josiassantos.desafio_anota_ai.services.aws.MessageDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AwsSnsService awsSnsService;

    public Category insert(CategoryDto categoryDto) {
        Category category = new Category(categoryDto);
        Category categorySalved = categoryRepository.save(category);
        awsSnsService.pubish(new MessageDto(categorySalved.toString()));

        return categorySalved;
    }

    public Optional<Category> getById(String id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category update(String id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        if (!categoryDto.title().isEmpty() || !categoryDto.title().trim().isBlank())
            category.setTitle(categoryDto.title());
        if (!categoryDto.description().isEmpty() || !categoryDto.description().trim().isBlank())
            category.setDescription(categoryDto.description());
        Category categorySalved = categoryRepository.save(category);
        awsSnsService.pubish(new MessageDto(categorySalved.toString()));

        return categorySalved;
    }

    public void delete(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.delete(category);
    }
}
