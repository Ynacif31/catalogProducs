package com.ygornacif.projetoCatalog.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ygornacif.projetoCatalog.DTO.ProductDTO;
import com.ygornacif.projetoCatalog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourcesTestsIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper jacksonObjectMapper;

    private long existId;
    private long notExistId;
    private long dependentId;

    @BeforeEach
    void setUp() throws Exception {
        existId = 1l;
        notExistId = 10000l;
        dependentId = 25l;
    }

    @Test
    public void findAllShouldReturnPageWhenIdExists() throws Exception {
        mockMvc.perform(get("/products?page=0&size=5&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(dependentId))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].name").value("Macbook Pro"))
                .andExpect(jsonPath("$.content[1].name").value("PC Gamer"));

    }

    @Test
    public void updateShouldReturnPageWhenIdExists() throws Exception {
        ProductDTO productDTO = Factory.createProductDTO();
        String jsonBody = jacksonObjectMapper.writeValueAsString(productDTO);

        String expectedName = productDTO.getName();
        String expectedDescription = productDTO.getDescription();

        mockMvc.perform(put("/products/{id}", existId).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andExpect(jsonPath("$.id").value(existId))
                .andExpect(jsonPath("$.name").value(expectedName))
                .andExpect(jsonPath("$.description").value(expectedDescription));

    }

    @Test
    public void updateShouldReturnPageWhenIdNotExists() throws Exception {
        ProductDTO productDTO = Factory.createProductDTO();
        String jsonBody = jacksonObjectMapper.writeValueAsString(productDTO);
        String expectedName = productDTO.getName();
        String expectedDescription = productDTO.getDescription();

        mockMvc.perform(put("/products/{id}", notExistId).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
