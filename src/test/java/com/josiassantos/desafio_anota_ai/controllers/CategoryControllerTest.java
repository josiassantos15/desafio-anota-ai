package com.josiassantos.desafio_anota_ai.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josiassantos.desafio_anota_ai.AutoDisplayNameGenerator;
import com.josiassantos.desafio_anota_ai.FactoryUtils;
import com.josiassantos.desafio_anota_ai.domain.category.Category;
import com.josiassantos.desafio_anota_ai.domain.category.CategoryDto;
import com.josiassantos.desafio_anota_ai.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.josiassantos.desafio_anota_ai.FactoryUtils.eletronicsCategoryDto;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(AutoDisplayNameGenerator.class)
class CategoryControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void insert_ShouldReturnCreatedCategory() throws Exception {
        CategoryDto requestDto = eletronicsCategoryDto;
        Category category = new Category(requestDto);

        when(categoryService.insert(any(CategoryDto.class))).thenReturn(category);

        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(category.getTitle()))
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andDo(print());
    }

    @Test
    void getAll_ShouldReturnCategories() throws Exception {
        Category category1 = FactoryUtils.eletronicsCategory;
        Category category2 = FactoryUtils.sportsCategory;

        when(categoryService.getAll()).thenReturn(List.of(category1, category2));

        mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value(category1.getTitle()))
                .andExpect(jsonPath("$[1].title").value(category2.getTitle()))
                .andDo(print());
    }

    @Test
    void update_ShouldReturnUpdatedCategory() throws Exception {
        String id = "123";
        CategoryDto updateDto = eletronicsCategoryDto;
        Category updatedCategory = new Category(updateDto);
        updatedCategory.setId(id);

        when(categoryService.update(eq(id), any(CategoryDto.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/api/category/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedCategory.getTitle()))
                .andExpect(jsonPath("$.id").value(id))
                .andDo(print());
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        String id = "123";

        mockMvc.perform(delete("/api/category/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertAll(
                () -> verify(categoryService).delete(id),
                () -> verifyNoMoreInteractions(categoryService)
        );
    }
}
