package com.iot.admin.admin.repository;

import java.util.Set;

import com.iot.admin.admin.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long>{

    Set<Category> findByIdIn(Set<Long> ids);

    Category findByName(String name);
    
}
