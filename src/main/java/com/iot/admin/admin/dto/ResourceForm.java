package com.iot.admin.admin.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.entity.ResourceType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceForm {

    @NotNull(message = "Tag doesn't be null.")
    @NotEmpty(message = "Tag not empty.")
    private String tag;

    @NotNull(message = "Name doesn't be null.")
    @NotEmpty(message = "Name not empty.")
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private String resource_type;

    @NotNull(message = "Device Parent doesn't be null.")
    private Long device_parent;


    public Resource getEntity(){
        Resource resource = new Resource();
        setEntity(resource);
        return resource;
    }

    public void setEntity(Resource resource){

        resource.setTag(tag);
        resource.setName(name);
        resource.setDescription(description);
        resource.setResourceType(ResourceType.valueOf(resource_type));
        
        assignRelationships(resource);
    }

    /**
     * Sets relationships with other entities.
     * 
     * @param resource resource entity instance.
     */
    private void assignRelationships(Resource resource) {
        Device deviceParentEntity = null;
        //Gateway gatewayEntity = null;

        if (device_parent != null)
            deviceParentEntity = new Device(device_parent);
        /*
        if (gateway != null)
            gatewayEntity = new Gateway(gateway);*/

        resource.setDeviceParent(deviceParentEntity);
        /*device.setGateway(gatewayEntity);*/
    }

    
}
