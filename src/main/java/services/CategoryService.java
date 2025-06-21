package services;

import domain.category.Category;
import domain.category.CategoryDto;
import domain.category.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category insert(CategoryDto categoryDto) {
        Category category = new Category(categoryDto);

        return categoryRepository.save(category);
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

        return categoryRepository.save(category);
    }

    public void delete(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.delete(category);
    }
}
