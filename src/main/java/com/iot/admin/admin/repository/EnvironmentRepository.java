package com.iot.admin.admin.repository;

import com.iot.admin.admin.entity.Environment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentRepository extends JpaRepository<Environment,Long> {
    
}
