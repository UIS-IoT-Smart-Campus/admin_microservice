package com.iot.admin.admin.service;

import javax.transaction.Transactional;

import com.iot.admin.admin.dto.ResourceDetails;
import com.iot.admin.admin.dto.ResourceForm;
import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    private ResourceRepository repository;


    @Override
    public ResourceDetails create(ResourceForm formData) {
        Resource resource = formData.getEntity();
        ResourceDetails resource_detail = new ResourceDetails();
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

    // Method for validate if propertie is already exists
    private boolean validateExist(Long id) {
        if (!repository.existsById(id)) {
            return false;
        } return true;
    }    
}
