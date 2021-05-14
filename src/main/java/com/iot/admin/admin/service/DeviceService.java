package com.iot.admin.admin.service;

import java.util.List;

import com.iot.admin.admin.dto.DeviceDetails;
import com.iot.admin.admin.dto.DeviceForm;

public interface DeviceService {    

    /**
     * Saves a new device data in database
     * 
     * @param formData device data to save
     */
    DeviceDetails create(DeviceForm formData);

    /**
     * 
     *Return list of devices created
     *@return {@link DeviceDetails}
    */
    List<DeviceDetails> findAll();


}
