package com.ygornacif.projetoCatalog.services;
import com.ygornacif.projetoCatalog.DTO.ProductDTO;
import com.ygornacif.projetoCatalog.entities.Category;
import com.ygornacif.projetoCatalog.entities.Product;
import com.ygornacif.projetoCatalog.entities.exceptions.DatabaseException;
import com.ygornacif.projetoCatalog.entities.exceptions.ResourceNotFoundException;
import com.ygornacif.projetoCatalog.repositories.CategoryRepository;
import com.ygornacif.projetoCatalog.repositories.ProductRepository;
import com.ygornacif.projetoCatalog.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServicesTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private Product product;
    private PageImpl<Product> page;
    private Category category;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));
        category = Factory.createCategory();
        productDTO = Factory.createProductDTO();

        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
        Mockito.when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(repository.save(Mockito.any(Product.class))).thenReturn(product);
    }

    @Test
    public void updateShouldReturnProductDTOWhenProductNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, productDTO);
        });

        Mockito.verify(repository, Mockito.times(1)).getReferenceById(nonExistingId);
    }

    @Test
    public void updateShouldReturnProductDTOWhenProductExists() {
        ProductDTO productDTO = Factory.createProductDTO();
        ProductDTO result = service.update(existingId, productDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        ProductDTO productDTO = service.findById(existingId);
        Assertions.assertNotNull(productDTO);

        Assertions.assertEquals(existingId, productDTO.getId());
        Mockito.verify(repository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
        Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void findAllPagedShouldReturnPage(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductDTO> page = service.findAllPaged(pageable);
        Assertions.assertNotNull(page);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void deleteShouldDataIntegrityViolationExceptionWhenDependentId(){
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });

    }

    @Test
    public void deleteShouldResourceNotFoundExceptionWhenIdNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldDoNothingWhenExistingId(){

        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

    }
}
