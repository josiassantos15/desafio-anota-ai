package services;

import domain.category.Category;
import domain.category.CategoryDto;
import exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repositories.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public Category insert(CategoryDto categoryDto) {
        Category category = new Category(categoryDto);

        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category update(String id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if(!categoryDto.title().isEmpty() || !categoryDto.title().trim().isBlank())
            category.setTitle(categoryDto.title());
        if(!categoryDto.description().isEmpty() || !categoryDto.description().trim().isBlank())
            category.setDescription(categoryDto.description());

        return categoryRepository.save(category);
    }

    public void delete(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.delete(category);
    }
}
