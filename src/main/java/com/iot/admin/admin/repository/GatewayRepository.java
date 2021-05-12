package com.iot.admin.admin.repository;


import com.iot.admin.admin.entity.Gateway;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {

    boolean existsByTag(String tag);

    Gateway findByTag(String tag);
    
    long deleteByTag(String tag);
    
}
