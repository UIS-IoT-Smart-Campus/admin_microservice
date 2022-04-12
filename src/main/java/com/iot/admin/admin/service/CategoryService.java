package com.iot.admin.admin.service;

import java.util.List;

import com.iot.admin.admin.dto.CategoryDetails;
import com.iot.admin.admin.dto.CategoryForm;

public interface CategoryService {

     /**
     * Saves a new device data in database
     * 
     * @param formData device data to save
     */
    CategoryDetails create(CategoryForm formData);

    /**
     * 
     *Return list of categories created
     *@return {@link CategoryDetails}
    */
    List<CategoryDetails> findAll();

    /**
     * 
     * @param fromData data from form
     * @param id is the current property id
     */
    CategoryDetails update(CategoryForm fromData, Long id);

    /**
     * Delete a category by id from database. Throws a exception if it doesn't 
     * exist.
     * 
     * @param id the category id.
     * @return True or falce
     */
    boolean delete(Long id);


    
    
}
