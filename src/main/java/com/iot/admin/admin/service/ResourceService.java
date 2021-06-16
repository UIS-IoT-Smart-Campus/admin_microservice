package com.iot.admin.admin.service;

import com.iot.admin.admin.dto.ResourceDetails;
import com.iot.admin.admin.dto.ResourceForm;

public interface ResourceService {

    /**
     * Saves a new resource data in database
     * 
     * @param formData resource data to save
     */
    ResourceDetails create(ResourceForm formData);
    
}
