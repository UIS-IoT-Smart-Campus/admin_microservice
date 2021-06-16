package com.iot.admin.admin.repository;

import com.iot.admin.admin.entity.Resource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource,Long>{
    
}
