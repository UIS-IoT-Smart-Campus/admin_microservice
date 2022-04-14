package com.iot.admin.admin.dto;

import java.util.ArrayList;
import java.util.List;

import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.entity.ResourceType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceDetails {

    private long id;
    private String name;
    private String description;
    private ResourceType type;
    private long device_parent;


    private List<PropertyDetails> properties = new ArrayList<>();

    public void setEntity(Resource resource){
        id = resource.getId();
        name = resource.getName();
        description = resource.getDescription();
        type = resource.getType();

        if(resource.getProperties()!=null){
            for(Property p:resource.getProperties()){
                PropertyDetails propertyDetails = new PropertyDetails();
                propertyDetails.setEntity(p);
                properties.add(propertyDetails);
            }
        }
        

        if (resource.getDeviceParent() != null) {
            device_parent = resource.getDeviceParent().getId();
        }
    }


    
}
