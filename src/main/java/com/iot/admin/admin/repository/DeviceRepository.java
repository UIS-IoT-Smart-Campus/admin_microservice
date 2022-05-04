package com.iot.admin.admin.repository;

import java.util.List;

import com.iot.admin.admin.entity.Device;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device,Long>{

    boolean existsByName(String name);

    List<Device> findByDeviceParent(Device deviceParent);

}
