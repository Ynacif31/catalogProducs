package com.ygornacif.projetoCatalog.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ygornacif.projetoCatalog.DTO.ProductDTO;
import com.ygornacif.projetoCatalog.entities.exceptions.DatabaseException;
import com.ygornacif.projetoCatalog.entities.exceptions.ResourceNotFoundException;
import com.ygornacif.projetoCatalog.services.ProductService;
import com.ygornacif.projetoCatalog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;
    private long existId;
    private long notExistId;
    private long dependentId;

    @Autowired
    private ObjectMapper jacksonObjectMapper;


    @BeforeEach
    void setUp() throws Exception {
        existId = 1l;
        notExistId = 2l;
        dependentId = 3l;
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));

        when(productService.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
        when(productService.findById(existId)).thenReturn(productDTO);
        when(productService.findById(notExistId)).thenThrow(ResourceNotFoundException.class);
        when(productService.update(eq(existId), any())).thenReturn(productDTO);
        when(productService.update(eq(notExistId), any())).thenThrow(ResourceNotFoundException.class);
        when(productService.insert(any())).thenReturn(productDTO);

        doNothing().when(productService).delete(existId);
        doThrow(ResourceNotFoundException.class).when(productService).delete(notExistId);
        doThrow(DatabaseException.class).when(productService).delete(dependentId);
    }

    @Test
    public void insertShouldReturnProductDTO() throws Exception {
        String jsonBody = jacksonObjectMapper.writeValueAsString(productDTO);
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        String jsonBody = jacksonObjectMapper.writeValueAsString(productDTO);

        mockMvc.perform(delete("/products/{id}", existId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String jsonBody = jacksonObjectMapper.writeValueAsString(productDTO);

        mockMvc.perform(delete("/products/{id}", notExistId).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        String jsonBody = jacksonObjectMapper.writeValueAsString(productDTO);

        mockMvc.perform(put("/products/{id}", existId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdDoesNotExists() throws Exception {
        String jsonBody = jacksonObjectMapper.writeValueAsString(productDTO);

        mockMvc.perform(put("/products/{id}", notExistId).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExist() throws Exception {
        mockMvc.perform(get("/products/{id}", existId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .exists()).andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdNotExist() throws Exception {
        mockMvc.perform(get("/products/{id}", notExistId)).andExpect(status().isNotFound());
    }
}
