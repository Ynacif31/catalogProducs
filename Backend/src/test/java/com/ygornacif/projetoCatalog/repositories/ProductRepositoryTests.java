package com.ygornacif.projetoCatalog.repositories;

import com.ygornacif.projetoCatalog.entities.Product;
import com.ygornacif.projetoCatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
public class ProductRepositoryTests {

    private long existId;
    private long countTotalProducts;
    private long notExistId;

    @BeforeEach
    void setUp() throws Exception {
        existId = 1L;
        countTotalProducts = 25L;
        notExistId = 1010L;
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveShouldPersistWithAutoIncrementIdWhenIdIsNull() {
            Product product = Factory.createProduct();
            product.setId(null);
            product = productRepository.save(product);

            Assertions.assertNotNull(product.getId());
            Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        productRepository.deleteById(existId);

        Optional<Product> productOptional = productRepository.findById(existId);
        Assertions.assertFalse(productOptional.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalProductNotWhenIdExists() {
        productRepository.findById(existId);

        Optional<Product> productOptional = productRepository.findById(existId);
        Assertions.assertTrue(productOptional.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalProductWhenIdNotExists() {
        productRepository.findById(notExistId);
        Optional<Product> productOptional = productRepository.findById(notExistId);
        Assertions.assertTrue(productOptional.isEmpty());
    }
}
