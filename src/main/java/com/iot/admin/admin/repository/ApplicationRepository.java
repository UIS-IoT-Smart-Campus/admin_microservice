package com.iot.admin.admin.repository;


import com.iot.admin.admin.entity.Application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application,Long>{
    
}
