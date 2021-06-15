package com.iot.admin.admin.dto;

import com.iot.admin.admin.entity.Property;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PropertyDetails {

    private long id;
    private String name;
    private String value;
    private String description;

    public void setEntity(Property property){
        id = property.getId();
        name = property.getName();
        value = property.getValue();
        description = property.getDescription();
    }
    
}
