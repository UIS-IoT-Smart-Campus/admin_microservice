package com.iot.admin.admin.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.iot.admin.admin.dto.DeviceDetails;
import com.iot.admin.admin.dto.DeviceForm;
import com.iot.admin.admin.dto.DeviceResourcePropertyForm;
import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;
import com.iot.admin.admin.service.DeviceService;
import com.iot.admin.admin.utils.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("device")
public class DeviceController {

    @Autowired
    private DeviceService service;

    @GetMapping
    public List<DeviceDetails> list(){       
        return service.findAll();
    }

    @GetMapping("pageable")
    public Map<String,Object> page_list(@RequestParam Map<String,String> params){
        Page<DeviceDetails> data = service.paginate(params);
        Map<String,Object> result= Pagination.mapPage(data);
        return result;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DeviceDetails create(@RequestBody @Valid DeviceForm data){        
        return service.create(data);
    }

    @GetMapping("/{id}")
    public DeviceDetails findById(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping("/gateway/{id}")
    public DeviceDetails getGatewayRepresentation(@PathVariable Long id){
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public DeviceDetails update(@RequestBody @Valid DeviceForm formData, @PathVariable Long id){
        return service.update(formData, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable Long id){        
        return service.delete(id);
    }

    @PostMapping("/resource/{device_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public boolean createResource(@RequestBody @Valid DeviceResourcePropertyForm data,@PathVariable Long device_id){        
        return service.addResource(data,device_id);
    }

    @DeleteMapping("/resource/{device_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public boolean deleteResource(@RequestBody @Valid DeviceResourcePropertyForm data,@PathVariable Long device_id){        
        return service.deleteResource(data,device_id);
    }

    @PostMapping("/property/{device_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PropertyDetails createProperty(@RequestBody @Valid PropertyForm data,@PathVariable Long device_id){        
        return service.addProperty(data,device_id);
    }

    @DeleteMapping("/property/{device_id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public boolean deletePorperty(@RequestBody @Valid DeviceResourcePropertyForm data,@PathVariable Long device_id){        
        return service.deleteProperty(data,device_id);
    }
    
}
