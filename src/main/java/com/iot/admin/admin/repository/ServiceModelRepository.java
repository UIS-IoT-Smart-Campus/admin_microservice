package com.iot.admin.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iot.admin.admin.entity.ServiceModel;

public interface ServiceModelRepository extends JpaRepository<ServiceModel,Long>{
    
}
