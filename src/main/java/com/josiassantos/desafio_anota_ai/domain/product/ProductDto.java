package com.josiassantos.desafio_anota_ai.domain.product;

public record ProductDto(
        String title,
        String description,
        String ownerId,
        Integer price,
        String categoryId
) {
}
