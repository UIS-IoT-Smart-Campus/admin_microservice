package com.iot.admin.admin.service;

import com.iot.admin.admin.dto.ResourceDetails;
import com.iot.admin.admin.dto.ResourceForm;
import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.errors.FieldException;
import com.iot.admin.admin.repository.DeviceRepository;
import com.iot.admin.admin.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    private ResourceRepository repository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public ResourceDetails create(ResourceForm formData) {

        // Validates device fields.
        validateFields(formData);

        Resource resource = formData.getEntity();
        ResourceDetails resource_detail = new ResourceDetails();
        resource_detail.setEntity(repository.save(resource));
        return resource_detail;
    }



    /**
     * Validates the given fields for a resource, throws an exception if any
     * validation fails.
     * 
     * @param formData the resource data to save.
     */
    private void validateFields(ResourceForm formData) {
        if(formData.getDevice_parent() != null)
            validateDeviceParent(formData.getDevice_parent());
    }

    /**
     * If the device ID is not null, checks if it exists in database.
     * 
     * @param deviceId the device ID, can be null.
     */
    private void validateDeviceParent(Long deviceId) {
        if (deviceId != null && !deviceRepository.existsById(deviceId)) {
            throw new FieldException("device_parent", "Device parent invalid");
        }
    }
    
}
