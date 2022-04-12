package com.iot.admin.admin.service;

import java.util.ArrayList;
import java.util.List;


import com.iot.admin.admin.dto.CategoryDetails;
import com.iot.admin.admin.dto.CategoryForm;
import com.iot.admin.admin.entity.Category;
import com.iot.admin.admin.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDetails> findAll() {
        Iterable<Category> list_devices = categoryRepository.findAll();
        List<CategoryDetails> list_details = new ArrayList<>();

        list_devices.forEach(category -> {
            CategoryDetails details = new CategoryDetails();
            details.setEntity(category);
            list_details.add(details);
        });

        return list_details;
    }
    
    @Override
    public CategoryDetails create(CategoryForm formData) {
        Category category = formData.getEntity();
        CategoryDetails category_details = new CategoryDetails();
        category_details.setEntity(categoryRepository.save(category));
        return category_details;        
    }

    @Override
    public CategoryDetails update(CategoryForm formData, Long id) {                
        
        Category category = categoryRepository.getById(id);
        formData.setEntity(category);
        CategoryDetails category_detail = new CategoryDetails();
        category_detail.setEntity(categoryRepository.save(category));
        return category_detail;
        
    }

    @Override
    public boolean delete(Long id) {
        categoryRepository.deleteById(id);
        return true;
    }
}
