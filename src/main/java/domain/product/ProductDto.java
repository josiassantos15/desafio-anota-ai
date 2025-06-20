package domain.product;

import domain.category.Category;

public record ProductDto(
        String id,
        String title,
        String description,
        String ownerId,
        Integer price,
        Category category
) {
}
