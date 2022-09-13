package com.iot.admin.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iot.admin.admin.dto.ServiceDetail;
import com.iot.admin.admin.dto.ServiceForm;
import com.iot.admin.admin.dto.ServiceResourceForm;
import com.iot.admin.admin.service.ServiceModelService;

@RestController
@RequestMapping("services")
public class ServiceModelController {

    @Autowired
    private ServiceModelService service;

    @GetMapping
    public List<ServiceDetail> list(){       
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServiceDetail create(@RequestBody @Valid ServiceForm data){        
        return service.create(data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable Long id){        
        return service.delete(id);
    }

    @PostMapping("/resource/{service_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public boolean createDevice(@RequestBody @Valid ServiceResourceForm data,@PathVariable Long service_id){        
        return service.addResource(data,service_id);
    }

    @DeleteMapping("/resource/{service_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public boolean deleteDevice(@RequestBody @Valid ServiceResourceForm data,@PathVariable Long service_id){        
        return service.deleteResource(data,service_id);
    }
    
}
