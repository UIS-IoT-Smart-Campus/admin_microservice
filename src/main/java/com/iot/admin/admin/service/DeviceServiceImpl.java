package com.iot.admin.admin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.admin.admin.dto.DeviceDetails;
import com.iot.admin.admin.dto.DeviceForm;
import com.iot.admin.admin.dto.DeviceResourcePropertyForm;
import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;
import com.iot.admin.admin.dto.ResourceDetails;
import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.errors.FieldException;
import com.iot.admin.admin.repository.DeviceRepository;
import com.iot.admin.admin.repository.PropertyRepository;
import com.iot.admin.admin.repository.ResourceRepository;
import com.iot.admin.admin.utils.Pagination;
import com.iot.admin.admin.utils.RestClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

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
    private DeviceRepository repository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private PropertyRepository propertyRepository;


    @Override
    public DeviceDetails create(DeviceForm formData) {
        // Validates device fields.
        validateFields(formData);
        Device device = formData.getEntity();
        DeviceDetails device_detail = new DeviceDetails();
        List<Device> devices = repository.findAll();
        Long new_id;
        if(devices.size()>0){
            Device last_device = devices.get(devices.size()-1);
            new_id = last_device.getId()+1L;
        } else {
            new_id = 1L;
        }
        device.setGlobal_id(new_id);
        if(device.getDeviceParent()!=null){
            Device deviceParent = repository.getById(device.getDeviceParent().getId());
            device.setDeviceParent(deviceParent);
        }    
        device = repository.save(device);
        device_detail.setEntity(device);
        //Synchronize Device
        Device deviceParent = device.getDeviceParent();        
        if(deviceParent!=null){
            for(Property prop:deviceParent.getProperties()){
                if(prop.getName().equals("gateway_ipv4")){
                    String ipv4 = prop.getValue();
                    String uri = "http://"+ipv4+":5000/device/create/api/";
                    String json = toJsonProperty(device_detail);
                    RestClient client = new RestClient();
                    client.post(uri, json);
                }            
            }
        }        
        return device_detail;
    }

    @Override
    public List<DeviceDetails> findAll() {
        Iterable<Device> list_devices = repository.findByDeviceParent(null);
        List<DeviceDetails> list_details = new ArrayList<>();

        list_devices.forEach(device -> {
            DeviceDetails details = new DeviceDetails();
            details.setEntity(device);
            list_details.add(details);
        });

        return list_details;
    }

    @Override
    public DeviceDetails findById(Long id) {
        Device device = repository.getById(id);
        DeviceDetails details = new DeviceDetails();
        details.setEntity(device);
        return details;
    }

    @Override
    public Page<DeviceDetails> paginate(Map<String,String> params) {
        Pageable pageRequest = Pagination.pageRequest(params);
        Page<Device> device_list = repository.findAll(pageRequest);
        List<DeviceDetails> deviceDetails = new ArrayList<>();
        for(Device d: device_list.getContent()){
            DeviceDetails device_details = new DeviceDetails();
            device_details.setEntity(d);
            deviceDetails.add(device_details);
        }
        Page<DeviceDetails> pageResult = new PageImpl<>(deviceDetails, pageRequest, device_list.getTotalElements());
        return pageResult;
    }


    
    @Override
    public DeviceDetails update(DeviceForm formData, Long id) {
        //New Tag validation.
        validateFields(formData);        
        Device device = repository.getById(id);       
        formData.setEntity(device);
        DeviceDetails device_detail = new DeviceDetails();
        device_detail.setEntity(repository.save(device));
        return device_detail;       
    }


    @Transactional
    @Override
    public boolean delete(Long id) {
        Device device = repository.getById(id);
        Device deviceParent = device.getDeviceParent();
        //Synchronize Device
        if(deviceParent!=null){
            for(Property prop:deviceParent.getProperties()){
                if(prop.getName().equals("gateway_ipv4")){
                    String ipv4 = prop.getValue();
                    String uri = "http://"+ipv4+":5000/device/delete/api/"+device.getGlobal_id().toString()+"/";
                    RestClient client = new RestClient();
                    client.delete(uri);
                }            
            }
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean addResource(DeviceResourcePropertyForm formData, Long id) {
        //New Tag validation.       
        Device device = repository.getById(id);
        Long resource_id = formData.getResource_id();
        Resource resource = resourceRepository.getById(resource_id);        
        Device deviceParent = device.getDeviceParent();
        //Synchronize Device
        if(deviceParent!=null){
            for(Property prop:deviceParent.getProperties()){
                if(prop.getName().equals("gateway_ipv4")){
                    String ipv4 = prop.getValue();
                    String uri = "http://"+ipv4+":5000/resources/api/";
                    ResourceDetails d_resource = new ResourceDetails();
                    d_resource.setEntity(resource);
                    String r_json = toJsonProperty(d_resource);
                    RestClient client = new RestClient();
                    client.post(uri, r_json);
                    String uri2 = "http://"+ipv4+":5000/device/resource/api/global/"+device.getGlobal_id().toString()+"/";
                    formData.setResource_id(resource.getGlobal_id());
                    String json = toJsonProperty(formData);
                    client.post(uri2, json);
                }            
            }
        }
        device.getResources().add(resource);
        repository.save(device);
        return true;       
    }

    @Override
    public boolean deleteResource(DeviceResourcePropertyForm formData, Long id) {
        //New Tag validation.       
        Device device = repository.getById(id);
        Long resource_id = formData.getResource_id();
        Resource resource = resourceRepository.getById(resource_id);        
        Device deviceParent = device.getDeviceParent();
        //Synchronize Device
        if(deviceParent!=null){
            for(Property prop:deviceParent.getProperties()){
                if(prop.getName().equals("gateway_ipv4")){
                    String ipv4 = prop.getValue();
                    String uri = "http://"+ipv4+":5000/device/resource/api/global/delete/"+device.getGlobal_id().toString()+"/";
                    String json = toJsonProperty(formData);
                    RestClient client = new RestClient();
                    client.post(uri, json);                    
                    String url2 = "http://"+ipv4+":5000/resources/api/global/"+resource.getGlobal_id().toString()+"/";
                    client.delete(url2);
                }            
            }
        }
        device.getResources().remove(resource);
        repository.save(device);
        resourceRepository.delete(resource);
        return true;       
    }

    @Override
    public PropertyDetails addProperty(PropertyForm formData, Long id) {
        //New Tag validation.       
        Device device = repository.getById(id);
        Property property = formData.getEntity();
        property.setProp_type("DEVICE");
        property.setParent_id(device.getId());
        List<Property> properties = propertyRepository.findAll();
        Long new_id;
        if(properties.size()>0){
            Property last_property = properties.get(properties.size()-1);
            new_id = last_property.getId()+1L;
        } else {
            new_id = 1L;
        }
        property.setGlobal_id(new_id); 
        property = propertyRepository.save(property);
        device.getProperties().add(property);
        repository.save(device);
        Device deviceParent = device.getDeviceParent();
        //Synchronize Device
        if(deviceParent!=null){
            for(Property prop:deviceParent.getProperties()){
                if(prop.getName().equals("gateway_ipv4")){
                    String ipv4 = prop.getValue();
                    String uri = "http://"+ipv4+":5000/device/property/global/api/"+device.getGlobal_id().toString()+"/";
                    String json = toJsonProperty(property);
                    RestClient client = new RestClient();
                    client.post(uri, json);
                }            
            }
        }
        PropertyDetails detail = new PropertyDetails();
        detail.setEntity(property);
        return detail;       
    }

    @Override
    public boolean deleteProperty(DeviceResourcePropertyForm formData, Long id) {
        //New Tag validation.       
        Device device = repository.getById(id);
        Long property_id = formData.getProperty_id();
        Property property = propertyRepository.getById(property_id);               
        Device deviceParent = device.getDeviceParent();
        //Synchronize Device
        if(deviceParent!=null){
            for(Property prop:deviceParent.getProperties()){
                if(prop.getName().equals("gateway_ipv4")){
                    String ipv4 = prop.getValue();
                    String uri = "http://"+ipv4+":5000/device/property/delete/api/global/"+property.getGlobal_id().toString()+"/";
                    RestClient client = new RestClient();
                    client.delete(uri);
                }            
            }
        }
        device.getProperties().remove(property); 
        repository.save(device);
        propertyRepository.delete(property);
        return true;       
    }

    


    /**
     * Validates the given fields for a device, throws an exception if any
     * validation fails.
     * 
     * @param formData the device data to save.
     */
    private void validateFields(DeviceForm formData) {
        validateDeviceParent(formData.getDevice_parent());
        if(repository.existsByName(formData.getName())){
            throw new FieldException("name", "Other device has the same name.");
        }
    }

   
    /**
     * If the device ID is not null, checks if it exists in database.
     * 
     * @param deviceId the device ID, can be null.
     */
    private void validateDeviceParent(Long deviceId) {
        if (deviceId != null && deviceId !=0 && !repository.existsById(deviceId)) {
            throw new FieldException("device_parent", "Device parent invalid");
        }
    }
}
