package com.iot.admin.admin.repository;

import com.iot.admin.admin.entity.Device;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device,Long>{    
}
