package com.iot.admin.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.iot.admin.admin.dto.DeviceDetails;
import com.iot.admin.admin.dto.DeviceForm;
import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.errors.FieldException;
import com.iot.admin.admin.repository.DeviceRepository;
import com.iot.admin.admin.repository.PropertyRepository;
import com.iot.admin.admin.repository.ResourceRepository;
import com.iot.admin.admin.utils.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private PropertyRepository propertiesRepository;
    @Autowired
    private ResourceRepository resourceRepository;


    @Override
    public DeviceDetails create(DeviceForm formData) {
        // Validates device fields.
        validateFields(formData);

        Device device = formData.getEntity();
        //device.setTag(generateTag(device.getName(),device.getIs_gateway()));
        DeviceDetails device_detail = new DeviceDetails();
        Device saveDevice = deviceRepository.save(device);

        // Guardar Propiedades
        if(device.getProperties()!=null){
            for(Property prop:device.getProperties()){
                prop.setDeviceParent(saveDevice);
                prop = propertiesRepository.save(prop);            
            }
        }
        
        // Guardar Recursos
        if(device.getResources()!=null){
            for(Resource resource:device.getResources()){

                //resource.setTag(generateResourceTag(resource.getName(), device.getTag()));
                resource.setDeviceParent(device);
                resource = resourceRepository.save(resource); 
                
                if(resource.getProperties()!=null){
                    for(Property propRe:resource.getProperties()){
                        propRe.setResourceParent(resource);
                        propRe = propertiesRepository.save(propRe);
                    }
                }
                
            }
        }
        
        device_detail.setEntity(device);
        return device_detail;

    }

    @Override
    public List<DeviceDetails> findAll() {
        Iterable<Device> list_devices = deviceRepository.findAll();
        List<DeviceDetails> list_details = new ArrayList<>();

        list_devices.forEach(device -> {
            DeviceDetails details = new DeviceDetails();
            details.setEntity(device);
            list_details.add(details);
        });

        return list_details;
    }


    @Override
    public Page<DeviceDetails> paginate(Map<String,String> params) {
        Pageable pageRequest = Pagination.pageRequest(params);
        Page<Device> device_list = deviceRepository.findAll(pageRequest);
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
        
        Device device = deviceRepository.getById(id);
        formData.setEntity(device);
        DeviceDetails device_detail = new DeviceDetails();
        device_detail.setEntity(deviceRepository.save(device));
        return device_detail;      
        
    }


    @Transactional
    @Override
    public boolean delete(Long id) {
        deviceRepository.deleteById(id);
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
    }


    /**
     * If the device ID is not null, checks if it exists in database.
     * 
     * @param deviceId the device ID, can be null.
     */
    private void validateDeviceParent(Long deviceId) {
        if (deviceId != null && deviceId !=0 && !deviceRepository.existsById(deviceId)) {
            throw new FieldException("device_parent", "Device parent invalid");
        }
    }

    
    /*
    //Metodo para generar TAGs para dispositivos.
    private String generateTag(String name,Boolean isGateway){

        String tagInit = "D";

        if(isGateway){
            tagInit = "DG";
        }
        
        String totalDevices = Long.toString(deviceRepository.count()+1);
        String tag = tagInit + name.substring(0,1).toUpperCase() +  totalDevices;
        return tag;
    }

    //Metodo para generar TAGs para dispositivos.
    private String generateResourceTag(String name,String deviceTag){        
        String totalDevices = Long.toString(deviceRepository.findByTag(deviceTag).getResources().size()+1);
        String tag = deviceTag + name.substring(0,1).toUpperCase() +  totalDevices;
        return tag;
    }*/

}
