package com.iot.admin.admin.service;

import java.util.List;

import com.iot.admin.admin.dto.ApplicationDetail;
import com.iot.admin.admin.dto.ApplicationForm;
import com.iot.admin.admin.dto.ApplicationServiceForm;
import com.iot.admin.admin.dto.DeviceApplicationForm;

public interface ApplicationService {

    /**
     * 
     *Return list of devices created
     *@return {@link DeviceDetails}
    */
    List<ApplicationDetail> getAll();

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

    /**
     * Add service to app
     * 
     * @param app_id the app id.
     * @param fromData service data
     * @return True or false
     */
    boolean addService(ApplicationServiceForm form,Long app_id);

    /**
     * Remove device from app
     * 
     * @param app_id the app id.
     * @param fromData service data
     * @return True or false
     */
    boolean deleteService(ApplicationServiceForm form,Long app_id);
    
}
