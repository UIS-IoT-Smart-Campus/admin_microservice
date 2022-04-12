package com.iot.admin.admin.service;

import java.util.List;
import java.util.Map;

import com.iot.admin.admin.dto.DeviceDetails;
import com.iot.admin.admin.dto.DeviceForm;

import org.springframework.data.domain.Page;

public interface DeviceService {    

    /**
     * Saves a new device data in database
     * 
     * @param formData device data to save
     */
    DeviceDetails create(DeviceForm formData);


    /**
     * 
     *Return list of devices created by pagination
     *@return
    */
    Page<DeviceDetails> paginate(Map<String,String> params);

    /**
     * 
     *Return list of devices created
     *@return {@link DeviceDetails}
    */
    List<DeviceDetails> findAll();


    /**
     * 
     * @param fromData data from form
     * @param id is the current device id
     */
    DeviceDetails update(DeviceForm fromData, Long id);

    /**
     * Delete a device by tag from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param id the device id.
     * @return True or falce
     */
    boolean delete(Long id);


}
