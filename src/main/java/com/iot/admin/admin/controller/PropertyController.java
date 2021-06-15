package com.iot.admin.admin.controller;

import javax.validation.Valid;

import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;
import com.iot.admin.admin.service.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("property")
public class PropertyController {

    @Autowired
    private PropertyService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PropertyDetails create(@RequestBody @Valid PropertyForm data){        
        return service.create(data);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid PropertyForm formData, @PathVariable Long id){
        service.update(formData, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){        
        service.deleteById(id);
    }
    
}
