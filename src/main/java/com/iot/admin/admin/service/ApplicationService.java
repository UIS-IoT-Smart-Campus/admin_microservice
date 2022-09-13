package com.iot.admin.admin.service;

import com.iot.admin.admin.dto.ApplicationDetail;
import com.iot.admin.admin.dto.ApplicationForm;
import com.iot.admin.admin.dto.DeviceApplicationForm;

public interface ApplicationService {

    /**
     * Saves a new app data in database
     * 
     * @param formData device data to save
     */
    ApplicationDetail create(ApplicationForm formData);

    /**
     * Delete a app by tag from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param id the app id.
     * @return True or false
     */
    boolean delete(Long id);

    /**
     * Add device to app
     * 
     * @param app_id the app id.
     * @param fromData data from form
     * @return True or false
     */
    boolean addDevice(DeviceApplicationForm form,Long app_id);

    /**
     * Remove device from app
     * 
     * @param app_id the app id.
     * @param fromData data from form
     * @return True or false
     */
    boolean deleteDevice(DeviceApplicationForm form,Long app_id);
    
}
