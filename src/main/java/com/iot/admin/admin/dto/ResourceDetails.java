package com.iot.admin.admin.dto;

import java.util.ArrayList;
import java.util.List;

import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.entity.Resource;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceDetails {

    private long id;
    private String tag;
    private String name;
    private String description;
    private long device_parent;

    private List<PropertyDetails> properties_list = new ArrayList<>();

    public void setEntity(Resource resource){
        id = resource.getId();
        tag = resource.getTag();
        name = resource.getName();
        description = resource.getDescription();

        if(resource.getProperties()!=null){
            for(Property p:resource.getProperties()){
                PropertyDetails propertyDetails = new PropertyDetails();
                propertyDetails.setEntity(p);
                properties_list.add(propertyDetails);
            }
        }
        

        if (resource.getDeviceParent() != null) {
            device_parent = resource.getDeviceParent().getId();
        }
    }


    
}
