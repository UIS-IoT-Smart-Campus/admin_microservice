package com.iot.admin.admin.controller;

import java.util.List;

import javax.validation.Valid;

import com.iot.admin.admin.dto.VirtualDeviceDetails;
import com.iot.admin.admin.dto.VirtualDeviceForm;
import com.iot.admin.admin.service.VirtualDeviceService;

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
@RequestMapping("virtualdevice")
public class VirtualDeviceController {

    @Autowired
    private VirtualDeviceService service;

    @GetMapping
    public List<VirtualDeviceDetails> list(){       
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public VirtualDeviceDetails create(@RequestBody @Valid VirtualDeviceForm data){        
        return service.create(data);
    }
    
    @PutMapping("/{id}")
    public VirtualDeviceDetails update(@RequestBody @Valid VirtualDeviceForm formData, @PathVariable Long id){
        return service.update(formData, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable Long id){        
        return service.deleteById(id);
    }

    
}
