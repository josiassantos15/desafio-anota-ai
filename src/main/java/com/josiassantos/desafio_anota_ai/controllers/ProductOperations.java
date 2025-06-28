package com.josiassantos.desafio_anota_ai.controllers;

import com.josiassantos.desafio_anota_ai.commons.GeneralOperations;
import com.josiassantos.desafio_anota_ai.domain.product.Product;
import com.josiassantos.desafio_anota_ai.domain.product.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Product Controller", description = "Manages all product resources")
public interface ProductOperations extends GeneralOperations {

    @Operation(summary = "Insert a category for products")
    ResponseEntity<Product> insert(
            @RequestBody(
                    description = "Request body with data for creating a product",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductDto.class)))
            ProductDto productDto);

    @Operation(summary = "List all products")
    ResponseEntity<List<Product>> getAll();

    @Operation(summary = "Update an existing product")
    ResponseEntity<Product> update(@PathVariable() String id,
                                   @RequestBody ProductDto productDto);

    @Operation(summary = "Remove a product by ID")
    ResponseEntity<Product> delete(@PathVariable() String id);
}
