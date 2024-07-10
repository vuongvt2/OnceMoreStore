package com.oncemore.store.service.impl;

import com.oncemore.store.entity.Category;
import com.oncemore.store.repository.CategoryRepository;
import com.oncemore.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
   private CategoryRepository repository;


    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }
}
