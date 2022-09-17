package com.iot.admin.admin.service;

import java.util.List;

import javax.transaction.Transactional;

import com.iot.admin.admin.dto.DeviceResourcePropertyForm;
import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;
import com.iot.admin.admin.dto.ResourceDetails;
import com.iot.admin.admin.dto.ResourceForm;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.repository.PropertyRepository;
import com.iot.admin.admin.repository.ResourceRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    private ResourceRepository repository;

    @Autowired
    private PropertyRepository propertyRepository;


    @Override
    public ResourceDetails create(ResourceForm formData) {
        Resource resource = formData.getEntity();
        ResourceDetails resource_detail = new ResourceDetails();
        List<Resource> resources = repository.findAll();
        Long new_id;
        if(resources.size()>0){
            Resource last_device = resources.get(resources.size()-1);
            new_id = last_device.getId()+1L;
        } else {
            new_id = 1L;
        }
        resource.setGlobal_id(new_id);
        resource = repository.save(resource);
        resource.setGlobal_id(resource.getId());
        resource = repository.save(resource);
        resource_detail.setEntity(resource);
        return resource_detail;
    }

    @Override
    public ResourceDetails findById(Long id) {
        Resource resource = repository.getById(id);
        ResourceDetails details = new ResourceDetails();
        details.setEntity(resource);
        return details;
    }

    @Override
    public void update(ResourceForm formData, Long id) {       
        Resource resource = repository.getById(id);
        formData.setEntity(resource);
        repository.save(resource);
    }

    @Transactional
    @Override
    public void deleteById(Long id){
        if(validateExist(id)){
            repository.deleteById(id);
        }        
    }

    @Override
    public PropertyDetails addProperty(PropertyForm formData, Long id) {
        //New Tag validation.       
        Resource resource = repository.getById(id);
        Property property = formData.getEntity();
        property.setProp_type("RESOURCE");
        property.setParent_id(resource.getId());
        List<Property> properties = propertyRepository.findAll();
        Long new_id;
        if(properties.size()>0){
            Property last_property = properties.get(properties.size()-1);
            new_id = last_property.getId()+1L;
        } else {
            new_id = 1L;
        }
        property.setGlobal_id(new_id); 
        property = propertyRepository.save(property);
        resource.getProperties().add(property);
        repository.save(resource);        
        PropertyDetails detail = new PropertyDetails();
        detail.setEntity(property);
        return detail;       
    }

    @Override
    public boolean deleteProperty(DeviceResourcePropertyForm formData, Long id) {
        //New Tag validation.       
        Resource resource = repository.getById(id);
        Long property_id = formData.getProperty_id();
        Property property = propertyRepository.getById(property_id);   
        resource.getProperties().remove(property); 
        repository.save(resource);
        propertyRepository.delete(property);
        return true;       
    }

    // Method for validate if propertie is already exists
    private boolean validateExist(Long id) {
        if (!repository.existsById(id)) {
            return false;
        } return true;
    }
    
    



}
