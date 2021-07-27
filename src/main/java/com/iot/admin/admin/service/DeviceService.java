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
     * Returun a device by tag
     * @return {@link DeviceDetails}
     */
    DeviceDetails findByTag(String tag);


    /**
     * 
     * @param fromData data from form
     * @param tag is the current device tag
     */
    DeviceDetails update(DeviceForm fromData, String tag);

    /**
     * Delete a device by tag from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param tag the device tag.
     * @return True or falce
     */
    boolean deleteByTag(String tag);


}
