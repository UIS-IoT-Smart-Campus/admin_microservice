package com.iot.admin.admin.service;

import java.util.List;

import com.iot.admin.admin.dto.ServiceDetail;
import com.iot.admin.admin.dto.ServiceForm;
import com.iot.admin.admin.dto.ServiceResourceForm;

public interface ServiceModelService {

    /**
     * 
     *Return list of devices created
     *@return {@link DeviceDetails}
    */
    List<ServiceDetail> getAll();

    /**
     * Saves a new app data in database
     * 
     * @param formData device data to save
     */
    ServiceDetail create(ServiceForm formData);

    /**
     * Delete a app by tag from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param id the app id.
     * @return True or false
     */
    boolean delete(Long id);

    /**
     * Add resource to service
     * 
     * @param service_id the service id.
     * @param fromData data from form
     * @return True or false
     */
    boolean addResource(ServiceResourceForm form,Long service_id);

    /**
     * Remove device from app
     * 
     * @param service_id the service id.
     * @param fromData data from form
     * @return True or false
     */
    boolean deleteResource(ServiceResourceForm form,Long service_id);
    
}
