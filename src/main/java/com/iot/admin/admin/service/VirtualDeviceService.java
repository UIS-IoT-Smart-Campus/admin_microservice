package com.iot.admin.admin.service;

import java.util.List;

import com.iot.admin.admin.dto.VirtualDeviceDetails;
import com.iot.admin.admin.dto.VirtualDeviceForm;

public interface VirtualDeviceService {

    /**
     * 
     *Return list of virtualDevices created
     *@return {@link VirtualDeviceDetails}
    */
    List<VirtualDeviceDetails> findAll();


    /**
     * Saves a new virtual device data in database
     * 
     * @param formData virtual device data to save
     */
    VirtualDeviceDetails create(VirtualDeviceForm formData);
    
    /**
     * 
     * @param fromData data from form
     * @param id is the current resorce id
     */
    VirtualDeviceDetails update(VirtualDeviceForm fromData, Long id);

    /**
     * Delete a virtual device by id from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param id the virtual device id.
     */
    Boolean deleteById(Long id);


}
