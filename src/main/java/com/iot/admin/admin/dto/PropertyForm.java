package com.iot.admin.admin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Property;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PropertyForm {

    @NotNull(message = "Name doesn't be null.")
    @NotEmpty(message = "Name not empty.")
    private String name;

    @NotNull(message = "Name doesn't be null.")
    @NotEmpty(message = "Name not empty.")
    private String value;

    @NotNull(message = "Type doesn't be null.")
    @NotEmpty(message = "Type not empty.")
    private String prop_type;

    @NotNull(message = "Parend Id doesn't be null.")
    @NotEmpty(message = "Parend Id  empty.")
    private Long parent_id;

    public Property getEntity(){
        Property property = new Property();
        setEntity(property);
        return property;
    }

    public void setEntity(Property property){
        property.setName(name);
        property.setValue(value);
        property.setProp_type(prop_type);
        property.setParent_id(parent_id);
    }

}
