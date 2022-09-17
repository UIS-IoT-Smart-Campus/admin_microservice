package com.iot.admin.admin.controller;

import javax.validation.Valid;

import com.iot.admin.admin.dto.DeviceResourcePropertyForm;
import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;
import com.iot.admin.admin.dto.ResourceDetails;
import com.iot.admin.admin.dto.ResourceForm;
import com.iot.admin.admin.service.ResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("resource")
public class ResourceController {


    @Autowired
    private ResourceService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResourceDetails create(@RequestBody @Valid ResourceForm data){        
        return service.create(data);
    } 
    
    @GetMapping("/{id}")
    public ResourceDetails findById(@PathVariable Long id){
        return service.findById(id);
    }
    
    @PutMapping("/{id}")
    public void update(@RequestBody @Valid ResourceForm formData, @PathVariable Long id){
        service.update(formData, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean deleteById(@PathVariable Long id){        
        service.deleteById(id);
        return true;
    }

    @PostMapping("/property/{resource_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PropertyDetails createProperty(@RequestBody @Valid PropertyForm data,@PathVariable Long resource_id){        
        return service.addProperty(data,resource_id);
    }

    @DeleteMapping("/property/{resource_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public boolean deletePorperty(@RequestBody @Valid DeviceResourcePropertyForm data,@PathVariable Long resource_id){        
        return service.deleteProperty(data,resource_id);
    }


}
