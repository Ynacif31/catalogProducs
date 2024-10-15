package com.ygornacif.projetoCatalog.services;

import com.ygornacif.projetoCatalog.entities.Category;
import com.ygornacif.projetoCatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<Category> findAll(){
      return categoryRepository.findAll();
    }
}
