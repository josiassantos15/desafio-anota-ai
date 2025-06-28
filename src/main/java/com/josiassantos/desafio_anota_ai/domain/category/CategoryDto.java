package com.josiassantos.desafio_anota_ai.domain.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category Data Transfer Object")
public record CategoryDto(
        @Schema(description = "Category Title", example = "sports")
        String title,

        @Schema(description = "Category Description", example = "Description for sports category")
        String description,

        @Schema(description = "Category Owner ID", example = "ew34-asd3-ew4w")
        String ownerId

) {
}
