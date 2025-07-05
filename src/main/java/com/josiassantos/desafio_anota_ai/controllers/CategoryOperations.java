package com.josiassantos.desafio_anota_ai.controllers;

import com.josiassantos.desafio_anota_ai.commons.GeneralOperations;
import com.josiassantos.desafio_anota_ai.domain.category.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Category Controller", description = "Manages all category resources")
public interface CategoryOperations extends GeneralOperations {

    @Operation(summary = "Insert a product category")
    ResponseEntity<CategoryDto> insert(
            @RequestBody(description = "Request body with data for creating a category", required = true)
            CategoryDto categoryDto
    );

    @Operation(summary = "List all categories")
    ResponseEntity<List<CategoryDto>> getAll();

    @Operation(summary = "Update an existing category")
    ResponseEntity<CategoryDto> update(
            @Parameter(description = "Category ID to be updated", required = true, example = "123")
            String id,
            @RequestBody(description = "Data for update category", required = true)
            CategoryDto categoryDto
    );

    @Operation(summary = "Remove a category by ID")
    ResponseEntity<Void> delete(
            @Parameter(description = "Category ID to be removed", required = true, example = "123")
            String id
    );
}
