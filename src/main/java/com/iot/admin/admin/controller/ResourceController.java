package com.iot.admin.admin.controller;

import javax.validation.Valid;

import com.iot.admin.admin.dto.ResourceDetails;
import com.iot.admin.admin.dto.ResourceForm;
import com.iot.admin.admin.service.ResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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
    
}
