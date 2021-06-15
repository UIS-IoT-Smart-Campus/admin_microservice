package com.iot.admin.admin.repository;

import com.iot.admin.admin.entity.Property;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property,Long>{
    
}
