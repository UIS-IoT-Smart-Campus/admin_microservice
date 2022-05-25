package com.iot.admin.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.iot.admin.admin.dto.EnvironmentDetails;
import com.iot.admin.admin.dto.EnvironmentForm;
import com.iot.admin.admin.entity.Environment;
import com.iot.admin.admin.repository.EnvironmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentServiceImpl implements EnvironmentService{

    @Autowired
    private EnvironmentRepository repository;

    @Override
    public EnvironmentDetails create(EnvironmentForm formData) {        
        Environment environment = formData.getEntity();
        EnvironmentDetails details = new EnvironmentDetails();
        details.setEntity(repository.save(environment));
        return details;
    }

    @Override
    public List<EnvironmentDetails> findAll() {
        Iterable<Environment> list_environment = repository.findAll();
        List<EnvironmentDetails> list_details = new ArrayList<>();

        list_environment.forEach(environment -> {
            EnvironmentDetails details = new EnvironmentDetails();
            details.setEntity(environment);
            list_details.add(details);
        });

        return list_details;
    }

    @Override
    public EnvironmentDetails findById(Long id) {
        Environment environment = repository.getById(id);
        EnvironmentDetails details = new EnvironmentDetails();
        details.setEntity(environment);
        return details;
    }

    @Override
    public EnvironmentDetails update(EnvironmentForm formData, Long id) {
        Environment environment = repository.getById(id);
        formData.setEntity(environment);
        EnvironmentDetails detail = new EnvironmentDetails();
        detail.setEntity(repository.save(environment));
        return detail;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return false;
    }

    

    
    
}
