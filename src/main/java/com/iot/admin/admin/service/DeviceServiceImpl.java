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
    private DeviceRepository deviceRepository;


    @Override
    public DeviceDetails create(DeviceForm formData) {

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
        if (deviceId != null && !deviceRepository.existsById(deviceId)) {
            throw new FieldException("device_parent", "Device parent invalid");
        }
    }


}
