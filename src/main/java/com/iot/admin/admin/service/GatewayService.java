package com.iot.admin.admin.service;

import java.util.List;
import java.util.Map;

import com.iot.admin.admin.dto.GatewayDetails;
import com.iot.admin.admin.dto.GatewayForm;

import org.springframework.data.domain.Page;

public interface GatewayService {

    /**
     * Saves a new gateway data in database
     * 
     * @param formData gateway data to save
     */
    GatewayDetails create(GatewayForm formData);

    /**
     * 
     * @param fromData data from form
     * @param tag is the current gateway tag
     */
    void update(GatewayForm fromData, String tag);

    /**
     * 
     *Return list of gateways created
     *@return
    */
    List<GatewayDetails> findAll();

    /**
     * 
     *Return list of gateways created by pagination
     *@return
    */
    Page<GatewayDetails> paginate(Map<String,String> params);


    /**
     * Find a gateway by tag from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param tag the gateway tag.
     * @return {@link GatewayDetails}
     */
    GatewayDetails findByTag(String tag);

    /**
     * Delete a gateway by tag from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param tag the gateway tag.
     * @return True or falce
     */
    boolean deleteByTag(String tag);
    
}
