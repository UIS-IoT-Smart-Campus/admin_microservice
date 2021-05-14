package com.iot.admin.admin.repository;

import com.iot.admin.admin.entity.Device;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device,Long>{

    boolean existsByTag(String tag);

    Device findByTag(String tag);
    
    long deleteByTag(String tag);
    
}
