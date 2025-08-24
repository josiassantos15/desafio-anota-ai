package com.josiassantos.desafio_anota_ai.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josiassantos.desafio_anota_ai.AutoDisplayNameGenerator;
import com.josiassantos.desafio_anota_ai.domain.product.Product;
import com.josiassantos.desafio_anota_ai.domain.product.ProductDto;
import com.josiassantos.desafio_anota_ai.services.ProductService;
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

import static com.josiassantos.desafio_anota_ai.FactoryUtils.*;
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
class ProductCrontrollerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductCrontroller productCrontroller;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productCrontroller).build();
    }

    @Test
    void insert_ShouldReturnCreatedProduct() throws Exception {
        ProductDto requestDto = eletronicProductDto;
        Product product = new Product(requestDto);

        when(productService.insert(any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.title").value(product.getTitle()))
                .andExpect(jsonPath("$.ownerId").value(product.getOwnerId()))
                .andDo(print());
    }

    @Test
    void getAll_ShouldReturnProducts() throws Exception {
        Product product1 = eletronicProduct;
        Product product2 = sportProduct;

        when(productService.getAll()).thenReturn(List.of(product1, product2));

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value(product1.getTitle()))
                .andExpect(jsonPath("$[1].title").value(product2.getTitle()))
                .andDo(print());
    }

    @Test
    void update_ShouldReturnUpdatedProduct() throws Exception {
        String id = "123";
        ProductDto productDto = eletronicProductDto;
        Product updatedProduct = new Product(productDto);
        updatedProduct.setId(id);

        when(productService.update(eq(id), any(ProductDto.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/product/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedProduct.getTitle()))
                .andExpect(jsonPath("$.id").value(id))
                .andDo(print());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        String id = "123";

        mockMvc.perform(delete("/api/product/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertAll(
                () -> verify(productService).delete(id),
                () -> verifyNoMoreInteractions(productService)
        );
    }
}