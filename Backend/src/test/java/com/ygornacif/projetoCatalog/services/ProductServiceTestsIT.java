package com.ygornacif.projetoCatalog.services;

import com.ygornacif.projetoCatalog.DTO.ProductDTO;
import com.ygornacif.projetoCatalog.entities.exceptions.ResourceNotFoundException;
import com.ygornacif.projetoCatalog.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Transactional
public class ProductServiceTestsIT {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Long existsId;
    private Long nonExistId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existsId = 1L;
        nonExistId = 10000L;
        countTotalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteResourcesWhenIdExists() {
        productService.delete(existsId);

        Assertions.assertEquals(countTotalProducts -1, productRepository.count());
    }

    @Test
    public void deleteShouldResourceNotFoundExceptionWhenIdNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.delete(nonExistId));
    }

    @Test
    public void findAllPageShouldReturnPageWhenSize0And10(){
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductDTO> result = productService.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());

    }

    @Test
    public void findAllPageShouldReturnEmptyPageWhenSizeNotExist(){
        PageRequest pageRequest = PageRequest.of(50, 10);
        Page<ProductDTO> result = productService.findAllPaged(pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPageShouldReturnSortedWhenPageSorted(){
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
        Page<ProductDTO> result = productService.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
        Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
        Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
    }


}
