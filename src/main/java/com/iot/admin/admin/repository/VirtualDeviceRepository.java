package com.iot.admin.admin.repository;

import java.util.List;

import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.VirtualDevice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualDeviceRepository extends JpaRepository<VirtualDevice,Long>{

    List<VirtualDevice> findByDeviceReference(Device deviceReference); 
    
}
