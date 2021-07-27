package com.iot.admin.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.iot.admin.admin.dto.DeviceDetails;
import com.iot.admin.admin.dto.DeviceForm;
import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.errors.FieldException;
import com.iot.admin.admin.repository.DeviceRepository;
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


    @Override
    public DeviceDetails create(DeviceForm formData) {

        System.out.println(formData);

        // Validates device fields.
        validateFields(formData);

        Device device = formData.getEntity();
        DeviceDetails device_detail = new DeviceDetails();
        device_detail.setEntity(deviceRepository.save(device));
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
    public DeviceDetails findByTag(String tag) {

        //Database Validations
        validateExistsTag(tag);

        DeviceDetails detail = new DeviceDetails();
        Device device = deviceRepository.findByTag(tag);
        detail.setEntity(device);
        return detail;
    }

    
    @Override
    public DeviceDetails update(DeviceForm formData, String tag) {

        //Database Validations
        validateExistsTag(tag);
        
        //New Tag validation.
        if(!tag.equalsIgnoreCase(formData.getTag())){
            validateFields(formData);
        }
        
        Device device = deviceRepository.findByTag(tag);
        formData.setEntity(device);
        DeviceDetails device_detail = new DeviceDetails();
        device_detail.setEntity(deviceRepository.save(device));
        return device_detail;      
        
    }


    @Transactional
    @Override
    public boolean deleteByTag(String tag) {
        long deleted = deviceRepository.deleteByTag(tag);
        return deleted > 0;
    }



    /**
     * Validates the given fields for a device, throws an exception if any
     * validation fails.
     * 
     * @param formData the device data to save.
     */
    private void validateFields(DeviceForm formData) {
        validateTag(formData.getTag());
        validateDeviceParent(formData.getDevice_parent());
    }

    // Method for validate if a new tag is already exists
    private void validateTag(String tag) {
        if (deviceRepository.existsByTag(tag.toUpperCase())) {
            throw new FieldException("tag", "The tag is already exist");
        }
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

    //Method to consult if tag exist
    private void validateExistsTag(String tag) {
        if (!deviceRepository.existsByTag(tag.toUpperCase())) {
            throw new FieldException("tag", "This TAG doesn't exist.");
        }
    }

}
