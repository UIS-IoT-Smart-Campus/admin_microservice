package com.iot.admin.admin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.entity.Resource;

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

    private String description;

    private Long device_parent;

    private Long resource_parent;

    public Property getEntity(){
        Property property = new Property();
        setEntity(property);
        return property;
    }

    public void setEntity(Property property){
        property.setName(name);
        property.setValue(value);
        property.setDescription(description);

        assignRelationships(property);
    }

    public void setUpdateEntity(Property property){
        property.setName(name);
        property.setValue(value);
        property.setDescription(description);
    }

    /**
     * Sets relationships with other entities.
     * 
     * @param property property entity instance.
     */
    private void assignRelationships(Property property) {
        Device propertyParentDevice = null;
        Resource propertyParentResource = null;

        if (device_parent != null){
            propertyParentDevice = new Device(device_parent);
            property.setDeviceParent(propertyParentDevice);
        }

        if (resource_parent != null){
            propertyParentResource = new Resource(resource_parent);
            property.setResourceParent(propertyParentResource);
        }
    }


    
}
