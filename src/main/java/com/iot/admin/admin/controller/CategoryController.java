package com.iot.admin.admin.controller;

import java.util.List;

import javax.validation.Valid;

import com.iot.admin.admin.dto.CategoryDetails;
import com.iot.admin.admin.dto.CategoryForm;
import com.iot.admin.admin.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class CategoryController {
    
    @Autowired
    private CategoryService service;


    @GetMapping
    public List<CategoryDetails> list(){       
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CategoryDetails create(@RequestBody @Valid CategoryForm data){        
        return service.create(data);
    }

    @PutMapping("/{id}")
    public CategoryDetails update(@RequestBody @Valid CategoryForm formData, @PathVariable Long id){
        return service.update(formData, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable Long id){        
        return service.delete(id);
    }

}
