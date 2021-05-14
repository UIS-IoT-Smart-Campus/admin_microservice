package com.iot.admin.admin.service;

import java.util.ArrayList;
import java.util.List;

import com.iot.admin.admin.dto.DeviceDetails;
import com.iot.admin.admin.dto.DeviceForm;
import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.errors.FieldException;
import com.iot.admin.admin.repository.DeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository repository;

    @Override
    public DeviceDetails create(DeviceForm formData) {
        
        //Validate if the tag device isn't created
        validateField(formData);

        Device device = formData.getEntity();
        System.out.println(device.getTag());
        DeviceDetails device_detail = new DeviceDetails();
        device_detail.setEntity(repository.save(device));        
        return device_detail;

    }

    @Override
    public List<DeviceDetails> findAll() {
         Iterable<Device> list_devices = repository.findAll();
         List<DeviceDetails> list_details = new ArrayList<>();
         
         list_devices.forEach(device-> {
            DeviceDetails details = new DeviceDetails();
            details.setEntity(device);
            list_details.add(details);
        });

        return list_details;
    }

    //Method for validate if a new tag is already
    private void validateField(DeviceForm formData) {
        if(repository.existsByTag(formData.getTag().toUpperCase())){
            throw new FieldException("tag", "The tag is already exist");
        }
    }
    
}
