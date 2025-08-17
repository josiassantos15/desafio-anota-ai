package com.josiassantos.desafio_anota_ai;

import com.josiassantos.desafio_anota_ai.domain.category.Category;
import com.josiassantos.desafio_anota_ai.domain.category.CategoryDto;

public class FactoryUtils {
    public static CategoryDto eletronicsCategoryDto = new CategoryDto(
            "1wse-er23dt-etr354",
            "Eletrônicos",
            "Dispositivos eletrônicos",
            "user123"
    );
    public static CategoryDto sportsCategoryDto = new CategoryDto(
            "2wse-er23dt-etr354",
            "Eletrônicos",
            "Dispositivos eletrônicos",
            "user123"
    );
    public static Category eletronicsCategory = new Category(eletronicsCategoryDto);
    public static Category sportsCategory = new Category(sportsCategoryDto);

}
