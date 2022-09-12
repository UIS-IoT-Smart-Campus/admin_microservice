package com.iot.admin.admin.dto;

import com.iot.admin.admin.entity.Property;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PropertyDetails {

    private long id;
    private long global_id;
    private String name;
    private String value;
    private String prop_type;
    private Long parent_id;

    public void setEntity(Property property){
        id = property.getId();
        global_id = property.getGlobal_id();
        name = property.getName();
        value = property.getValue();
        prop_type = property.getProp_type();
        parent_id = property.getParent_id();
    }
    
}
