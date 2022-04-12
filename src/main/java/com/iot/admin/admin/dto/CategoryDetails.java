package com.iot.admin.admin.dto;

import com.iot.admin.admin.entity.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDetails {

    private long id;
    private String name;
    private String description;

    public void setEntity(Category category){
        id = category.getId();
        name = category.getName();
        description = category.getDescription();
    }
    
}
