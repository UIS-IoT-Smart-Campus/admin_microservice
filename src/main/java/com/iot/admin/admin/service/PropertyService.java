package com.iot.admin.admin.service;

import com.iot.admin.admin.dto.PropertyDetails;
import com.iot.admin.admin.dto.PropertyForm;

public interface PropertyService {

    /**
     * Saves a new property data in database
     * 
     * @param formData property data to save
     */
    PropertyDetails create(PropertyForm formData);

    /**
     * Fetch a property by ID.
     * 
     * @param id ID to find
     * @return
     */
    PropertyDetails findById(Long id);

    /**
     * 
     * @param fromData data from form
     * @param id is the current property id
     */
    void update(PropertyForm fromData, Long id);

    /**
     * Delete a property by id from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param id the property id.
     * @return True or falce
     */
    void deleteById(Long id);

}
