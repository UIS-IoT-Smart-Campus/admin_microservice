package com.iot.admin.admin.service;

import java.io.IOException;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;
import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.errors.FieldException;
import com.iot.admin.admin.repository.DeviceRepository;
import com.iot.admin.admin.repository.PropertyRepository;
import com.iot.admin.admin.utils.RestClient;

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

    @Autowired
    private DeviceRepository deviceRepository;


    @Override
    public PropertyDetails create(PropertyForm formData) {

        // Validates device fields.
        validateFields(formData);

        Property property = formData.getEntity();
        PropertyDetails property_detail = new PropertyDetails();
        property_detail.setEntity(repository.save(property));
        return property_detail;
    }


    @Override
    public void update(PropertyForm formData, Long id) {
        // Validates device fields.
        validateFields(formData);
        Property property = repository.getById(id);
        formData.setUpdateEntity(property);
        repository.save(property);
        //Sincronizar cambio en dispositivo        
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


    /**
     * Validates the given fields for a property, throws an exception if any
     * validation fails.
     * 
     * @param formData the property data to save.
     */
    private void validateFields(PropertyForm formData) {
        if(formData.getDevice_parent() != null)
            validateDeviceParent(formData.getDevice_parent());
        //if(formData.getResource_parent() != null)
            //validateResourceParent(formData.getResource_parent());

    }


    /**
     * If the device ID is not null, checks if it exists in database.
     * 
     * @param deviceId the device ID, can be null.
     */
    private void validateDeviceParent(Long deviceId) {
        if (deviceId != null && !deviceRepository.existsById(deviceId)) {
            throw new FieldException("device_parent", "Device parent invalid");
        }
    }

    // Method for validate if propertie is already exists
    private boolean validateExist(Long id) {
        if (!repository.existsById(id)) {
            return false;
        } return true;
    }

    
}
