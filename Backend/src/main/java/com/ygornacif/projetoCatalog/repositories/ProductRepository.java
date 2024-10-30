package com.ygornacif.projetoCatalog.repositories;

import com.ygornacif.projetoCatalog.entities.Category;
import com.ygornacif.projetoCatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
