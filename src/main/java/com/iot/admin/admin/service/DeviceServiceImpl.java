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
    private DeviceRepository repository;    


    @Override
    public DeviceDetails create(DeviceForm formData) {
        // Validates device fields.
        validateFields(formData);
        Device device = formData.getEntity();
        DeviceDetails device_detail = new DeviceDetails();       
        device = repository.save(device);
        device.setGlobal_id(device.getId());
        device = repository.save(device);
        device_detail.setEntity(device);
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
        repository.deleteById(id);
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
