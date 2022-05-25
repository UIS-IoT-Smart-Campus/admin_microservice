package com.iot.admin.admin.service;

import java.util.List;

import com.iot.admin.admin.dto.EnvironmentDetails;
import com.iot.admin.admin.dto.EnvironmentForm;

public interface EnvironmentService {

    /**
     * Saves a new environment data in database
     * 
     * @param formData enviroment data to save
     */
    EnvironmentDetails create(EnvironmentForm formData);

    /**
     * 
     *Return list of environments created
     *@return {@link EnvironmentDetails}
    */
    List<EnvironmentDetails> findAll();

    /**
     * Returns a environment by ID.
     * @return
     */
    EnvironmentDetails findById(Long id);

    /**
     * 
     * @param fromData data from form
     * @param id is the current environment id
     */
    EnvironmentDetails update(EnvironmentForm fromData, Long id);

    /**
     * Delete a environment by id from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param id the environment id.
     * @return True or falce
     */
    boolean delete(Long id);
}
