package com.iot.admin.admin.service;

import java.io.IOException;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.repository.PropertyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService{

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
    private PropertyRepository repository;

    @Override
    public PropertyDetails create(PropertyForm formData) {
        Property property = formData.getEntity();
        PropertyDetails property_detail = new PropertyDetails();
        property = repository.save(property);
        property.setGlobal_id(property.getId());
        property = repository.save(property);        
        property_detail.setEntity(property);
        return property_detail;
    }


    @Override
    public void update(PropertyForm formData, Long id) {
        // Validates device fields.
        Property property = repository.getById(id);
        formData.setEntity(property);
        repository.save(property);
        //Sincronizar cambio en dispositivo
        /*  
        Device device_parent = property.getDeviceParent();      
        if(device_parent!=null){            
            for(Property prop:device_parent.getProperties()){
                if(prop.getName().equals("gateway_ipv4")){
                    String ipv4 = prop.getValue();
                    String uri = "http://"+ipv4+":5000/device/property/api/"+property.getId().toString()+"/";
                    System.out.println(uri);
                    PropertyDetails prop_d = new PropertyDetails();
                    prop_d.setEntity(property);
                    String json = toJsonProperty(prop_d);
                    RestClient client = new RestClient();
                    client.put(uri, json);
                }
            }
                    
        }
         */  
    }


    @Transactional
    @Override
    public void deleteById(Long id){
        if(validateExist(id)){
            repository.deleteById(id);
        }        
    }

    @Override
    public PropertyDetails findById(Long id) {
        Property property = repository.getById(id);
        PropertyDetails details = new PropertyDetails();
        details.setEntity(property);
        return details;
    }

    // Method for validate if propertie is already exists
    private boolean validateExist(Long id) {
        if (!repository.existsById(id)) {
            return false;
        } return true;
    }    
}
