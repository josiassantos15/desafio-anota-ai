package domain.product;

public record ProductDto(
        String title,
        String description,
        String ownerId,
        Integer price,
        String categoryId
) {
}
