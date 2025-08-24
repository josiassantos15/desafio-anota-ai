package com.josiassantos.desafio_anota_ai;

import com.josiassantos.desafio_anota_ai.domain.category.Category;
import com.josiassantos.desafio_anota_ai.domain.category.CategoryDto;
import com.josiassantos.desafio_anota_ai.domain.product.Product;
import com.josiassantos.desafio_anota_ai.domain.product.ProductDto;

import java.math.BigDecimal;

public class FactoryUtils {
    public static CategoryDto eletronicsCategoryDto = new CategoryDto(
            "1wse-er23dt-etr354",
            "Eletrônicos",
            "Dispositivos eletrônicos",
            "user123"
    );
    public static CategoryDto sportsCategoryDto = new CategoryDto(
            "2wse-er23dt-etr354",
            "Esportivos",
            "Categoria de materiais esportivos",
            "user123"
    );
    public static ProductDto eletronicProductDto = new ProductDto(
            "3wse-er23dt-etr345",
            "TV 70",
            "Televisor de 50 polegadas XPTO",
            "user123",
            BigDecimal.TEN,
            "1wse-er23dt-etr354"
    );
    public static ProductDto sportProductDto = new ProductDto(
            "4wse-er23dt-etr345",
            "Bola futebol",
            "Bola para futebol de campo",
            "user123",
            BigDecimal.TEN,
            "2wse-er23dt-etr354"
    );
    public static Category eletronicsCategory = new Category(eletronicsCategoryDto);
    public static Category sportsCategory = new Category(sportsCategoryDto);
    public static Product eletronicProduct = new Product(eletronicProductDto);
    public static Product sportProduct = new Product(sportProductDto);

}
