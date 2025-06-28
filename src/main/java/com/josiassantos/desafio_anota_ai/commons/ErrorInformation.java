package com.josiassantos.desafio_anota_ai.commons;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorInformation(
        @Schema(description = "Status Http", example = "500")
        Integer httpStatus,
        @Schema(description = "Resource that generated the error", example = "/genericResource")
        String url,
        @Schema(description = "Error message caught by the exception", example = "Internal Server error")
        String errorMessage
) {
}