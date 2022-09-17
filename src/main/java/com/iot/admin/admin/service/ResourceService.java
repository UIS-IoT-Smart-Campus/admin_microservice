package com.iot.admin.admin.service;

import com.iot.admin.admin.dto.DeviceResourcePropertyForm;
import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;
import com.iot.admin.admin.dto.ResourceDetails;
import com.iot.admin.admin.dto.ResourceForm;

public interface ResourceService {

    /**
     * Saves a new resource data in database
     * 
     * @param formData resource data to save
     */
    ResourceDetails create(ResourceForm formData);

    /**
     * Fetch a property by ID.
     * 
     * @param id ID to find
     * @return
     */
    ResourceDetails findById(Long id);

     /**
     * 
     * @param fromData data from form
     * @param id is the current resorce id
     */
    void update(ResourceForm fromData, Long id);

    /**
     * Delete a resource by id from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param id the resource id.
     */
    void deleteById(Long id);

    /**
     * Add property to resource
     * 
     * @param device_id the resource id.
     * @param fromData data from form
     * @return True or false
     */
    PropertyDetails addProperty(PropertyForm form,Long resource_id);

     /**
     * Delete property to resource
     * 
     * @param device_id the resource id.
     * @param fromData data from form
     * @return True or false
     */
    boolean deleteProperty(DeviceResourcePropertyForm form,Long resource_id);
    
}
