package com.josiassantos.desafio_anota_ai.domain.product;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Product Data Transfer Object")
public record ProductDto(
        @Schema(description = "", example = "")
        String id,

        @Schema(description = "Product title", example = "Ball")
        String title,

        @Schema(description = "Product description", example = "Soccer Ball")
        String description,

        @Schema(description = "Product owner ID", example = "34gh-re34-43tr")
        String ownerId,

        @Schema(description = "Product price", example = "50.10")
        BigDecimal price,

        @Schema(description = "Product category ID", example = "ew34-asd3-ew4w")
        String categoryId
) {
    public ProductDto(Product product) {
        this(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getOwnerId(),
                product.getPrice(),
                product.getCategory()
        );
    }
}
