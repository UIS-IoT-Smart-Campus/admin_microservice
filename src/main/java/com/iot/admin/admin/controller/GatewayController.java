package com.iot.admin.admin.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.iot.admin.admin.dto.GatewayDetails;
import com.iot.admin.admin.dto.GatewayForm;
import com.iot.admin.admin.service.GatewayService;
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
@RequestMapping("gateway")
public class GatewayController {

    @Autowired
    private GatewayService service;
    
    @GetMapping
    public List<GatewayDetails> list(){        
        return service.findAll();
    }

    @GetMapping("pageable")
    public Map<String,Object> page_list(@RequestParam Map<String,String> params){
        Page<GatewayDetails> data = service.paginate(params);
        Map<String,Object> result= Pagination.mapPage(data);
        return result;
    }

    @GetMapping("/tag/{tag}")
    public GatewayDetails findByTag(@PathVariable final String tag){        
        return service.findByTag(tag);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GatewayDetails create(@RequestBody @Valid GatewayForm data){
        return service.create(data);
    }

    @PutMapping("/tag/{tag}")
    public void update(@RequestBody @Valid GatewayForm formData, @PathVariable String tag){
        tag = tag.toUpperCase();
        service.update(formData, tag);
    }


    @DeleteMapping("/tag/{tag}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean deleteByTag(@PathVariable String tag){        
        return service.deleteByTag(tag);
    }

}
