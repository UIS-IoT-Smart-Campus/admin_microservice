package com.iot.admin.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.iot.admin.admin.dto.GatewayDetails;
import com.iot.admin.admin.dto.GatewayForm;
import com.iot.admin.admin.entity.Gateway;
import com.iot.admin.admin.errors.FieldException;
import com.iot.admin.admin.repository.GatewayRepository;
import com.iot.admin.admin.utils.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class GatewayServiceImpl implements GatewayService {

    @Autowired
    private GatewayRepository repository;

    @Override
    public GatewayDetails create(GatewayForm formData) {
        
        //Database Validations
        validateField(formData);    
        
        Gateway gateway = formData.getEntity();
        GatewayDetails details = new GatewayDetails();
        details.setEntity(repository.save(gateway));
        
        return details;
    }

    
    @Override
    public List<GatewayDetails> findAll() {
        Iterable<Gateway> list_gateways = repository.findAll();
        List<GatewayDetails> list_details = new ArrayList<>();        

        list_gateways.forEach(gateway-> {
            GatewayDetails details = new GatewayDetails();
            details.setEntity(gateway);
            list_details.add(details);
        });

        return list_details;
    }

    @Override
    public Page<GatewayDetails> paginate(Map<String,String> params) {
        Pageable pageRequest = Pagination.pageRequest(params);
        Page<Gateway> gateway_list = repository.findAll(pageRequest);
        List<GatewayDetails> gatewayDetails = new ArrayList<>();
        for(Gateway g: gateway_list.getContent()){
            GatewayDetails gateway_detail = new GatewayDetails();
            gateway_detail.setEntity(g);
            gatewayDetails.add(gateway_detail);
        }
        Page<GatewayDetails> pageResult = new PageImpl<>(gatewayDetails, pageRequest, gateway_list.getTotalElements());
        return pageResult;
    }

    @Override
    public GatewayDetails findByTag(String tag) {

        //Database Validations
        validateTag(tag);

        GatewayDetails detail = new GatewayDetails();
        Gateway gateway = repository.findByTag(tag);
        detail.setEntity(gateway);
        return detail;
    }


    
    @Transactional
    @Override
    public boolean deleteByTag(String tag) {
        long deleted = repository.deleteByTag(tag);
        return deleted > 0;
    }

    @Override
    public void update(GatewayForm formData, String tag) {

        //Database Validations
        validateTag(tag);

        if(!tag.equalsIgnoreCase(formData.getTag())){
            validateField(formData);
        }
        
        Gateway gateway = repository.findByTag(tag);
        formData.setEntity(gateway);        
        repository.save(gateway);
    }


    //Method for validate if a new tag is already
    private void validateField(GatewayForm formData) {
        if(repository.existsByTag(formData.getTag().toUpperCase())){
            throw new FieldException("tag", "The tag is already exist");
        }
    }

    //Method for validate if a tag is aready exist
    private void validateTag(String tag) {
        if(!repository.existsByTag(tag)){
            throw new EntityNotFoundException("The tag doen't exist.");
        }
    }

    
}
