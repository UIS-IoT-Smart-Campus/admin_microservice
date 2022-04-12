package com.iot.admin.admin.repository;

import com.iot.admin.admin.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long>{
    
}
