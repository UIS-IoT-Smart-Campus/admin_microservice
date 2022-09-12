package com.iot.admin.admin.dto;

import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.entity.ResourceType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceDetails {

    private long id;
    private long global_id;
    private String name;
    private String description;
    private ResourceType resource_type;
    private long device_parent;


    public void setEntity(Resource resource){
        id = resource.getId();
        global_id = resource.getGlobal_id();
        name = resource.getName();
        description = resource.getDescription();
        resource_type = resource.getResource_type();
    }    
}
