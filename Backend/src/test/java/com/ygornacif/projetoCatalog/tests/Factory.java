package com.ygornacif.projetoCatalog.tests;

import com.ygornacif.projetoCatalog.DTO.ProductDTO;
import com.ygornacif.projetoCatalog.entities.Category;
import com.ygornacif.projetoCatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {

        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "www.img.url", Instant.parse(Instant.now().toString()));
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory() {
        return new Category(1l, "Eletronics");
    }
}
