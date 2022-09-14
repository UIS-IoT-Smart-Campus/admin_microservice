package com.iot.admin.admin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.admin.admin.dto.ResourceDetails;
import com.iot.admin.admin.dto.ServiceDetail;
import com.iot.admin.admin.dto.ServiceForm;
import com.iot.admin.admin.dto.ServiceResourceForm;
import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.entity.ServiceModel;
import com.iot.admin.admin.repository.ResourceRepository;
import com.iot.admin.admin.repository.ServiceModelRepository;
import com.iot.admin.admin.utils.RedisClient;



@Service
public class ServiceModelServiceImpl implements ServiceModelService{

    public String toJsonProperty(Object prop){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(prop);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
        
    }

    @Autowired
    private ServiceModelRepository repository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public List<ServiceDetail> getAll(){
        Iterable<ServiceModel> list_services_model = repository.findAll();
        List<ServiceDetail> list_details = new ArrayList<>();

        list_services_model.forEach(service_model -> {
            ServiceDetail details = new ServiceDetail();
            details.setEntity(service_model);
            list_details.add(details);
        }); 
        return list_details;
    }

    @Override
    public ServiceDetail create(ServiceForm formData) {
        // Validates device fields.
        ServiceModel service_model = formData.getEntity();
        ServiceDetail service_detail = new ServiceDetail();
        List<ServiceModel> services = repository.findAll();
        Long new_id;
        if(services.size()>0){
            ServiceModel last_service_model = services.get(services.size()-1);
            new_id = last_service_model.getId()+1L;
        } else {
            new_id = 1L;
        }
        service_model.setGlobal_id(new_id);
        service_detail.setEntity(repository.save(service_model));
        //Add queue notification SDA
        RedisClient r_client = new RedisClient();
        String service_json = toJsonProperty(service_detail);
        r_client.put("service", "create", service_json);
        //End queue notification SDA
        return service_detail;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        //Add queue notification SDA
        RedisClient r_client = new RedisClient();
        ServiceModel service_model = repository.getById(id);
        ServiceDetail service_model_detail = new ServiceDetail();
        service_model_detail.setEntity(service_model);
        String service_json = toJsonProperty(service_model_detail);
        r_client.put("service", "delete", service_json);
        //End queue notification SDA
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean addResource(ServiceResourceForm formData,Long service_id) {
        ServiceModel service_model = repository.getById(service_id);
        Long resource_id = formData.getResource_id();
        Resource resource = resourceRepository.getById(resource_id);               
        service_model.getResources().add(resource);
        repository.save(service_model);
        //Add queue notification SDA
        RedisClient r_client = new RedisClient();
        ResourceDetails resource_detail = new ResourceDetails();
        resource_detail.setEntity(resource);
        String resource_json = toJsonProperty(resource_detail);
        r_client.put("resource", "create", resource_json);
        Map<String, String> json_content = new HashMap<String, String>();
        json_content.put("service_id", service_model.getGlobal_id().toString());
        json_content.put("resource_id", resource.getGlobal_id().toString());
        String messaje_json = toJsonProperty(json_content);
        r_client.put("service_resource", "create", messaje_json);
        //End queue notification SDA
        return true;
    }

    @Override
    public boolean deleteResource(ServiceResourceForm formData,Long service_id) {
        ServiceModel service_model = repository.getById(service_id);
        Long resource_id = formData.getResource_id();
        Resource resource = resourceRepository.getById(resource_id);      
        service_model.getResources().remove(resource);
        repository.save(service_model);
        //Add queue notification SDA
        RedisClient r_client = new RedisClient();        
        Map<String, String> json_content = new HashMap<String, String>();
        json_content.put("service_id", service_model.getGlobal_id().toString());
        json_content.put("resource_id", resource.getGlobal_id().toString());
        String messaje_json = toJsonProperty(json_content);
        r_client.put("service_resource", "delete", messaje_json);
        ResourceDetails resource_detail = new ResourceDetails();
        resource_detail.setEntity(resource);
        String resource_json = toJsonProperty(resource_detail);
        r_client.put("resource", "delete", resource_json);
        //End queue notification SDA
        return true;
    }
    
}
