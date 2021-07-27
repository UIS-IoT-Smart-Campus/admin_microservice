package com.iot.admin.admin.service;

import javax.transaction.Transactional;

import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.errors.FieldException;
import com.iot.admin.admin.repository.DeviceRepository;
import com.iot.admin.admin.repository.PropertyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService{

    @Autowired
    private PropertyRepository repository;

    @Autowired
    private DeviceRepository deviceRepository;


    @Override
    public PropertyDetails create(PropertyForm formData) {

        // Validates device fields.
        validateFields(formData);

        Property property = formData.getEntity();
        PropertyDetails property_detail = new PropertyDetails();
        property_detail.setEntity(repository.save(property));
        return property_detail;
    }


    @Override
    public void update(PropertyForm formData, Long id) {
        // Validates device fields.
        validateFields(formData);
        Property property = repository.getOne(id);
        formData.setEntity(property);
        repository.save(property);
    }


    @Transactional
    @Override
    public void deleteById(Long id){
        if(validateExist(id)){
            repository.deleteById(id);
        }        
    }

    


    /**
     * Validates the given fields for a property, throws an exception if any
     * validation fails.
     * 
     * @param formData the property data to save.
     */
    private void validateFields(PropertyForm formData) {
        if(formData.getDevice_parent() != null)
            validateDeviceParent(formData.getDevice_parent());
        //if(formData.getResource_parent() != null)
            //validateResourceParent(formData.getResource_parent());

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

    // Method for validate if propertie is already exists
    private boolean validateExist(Long id) {
        if (!repository.existsById(id)) {
            return false;
        } return true;
    }

    
}
