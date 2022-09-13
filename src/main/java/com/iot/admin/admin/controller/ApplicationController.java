package com.iot.admin.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iot.admin.admin.dto.ApplicationDetail;
import com.iot.admin.admin.dto.ApplicationForm;
import com.iot.admin.admin.dto.DeviceApplicationForm;
import com.iot.admin.admin.service.ApplicationService;

@RestController
@RequestMapping("apps")
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApplicationDetail create(@RequestBody @Valid ApplicationForm data){        
        return service.create(data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable Long id){        
        return service.delete(id);
    }

    @PostMapping("/device/{app_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public boolean createDevice(@RequestBody @Valid DeviceApplicationForm data,@PathVariable Long app_id){        
        return service.addDevice(data,app_id);
    }

    @DeleteMapping("/device/{app_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public boolean deleteDevice(@RequestBody @Valid DeviceApplicationForm data,@PathVariable Long app_id){        
        return service.deleteDevice(data,app_id);
    }


    
}
