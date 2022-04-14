package com.iot.admin.admin.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.entity.ResourceType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceForm {


    @NotNull(message = "Name can't be null.")
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    private String description;

    @NotNull(message = "Type can't be null.")
    @NotEmpty(message = "Type can't empty.")
    @Enumerated(EnumType.STRING)
    private String type;

    @NotNull(message = "Device Parent doesn't be null.")
    private Long device_parent;

    private List<PropertyForm> properties;


    public Resource getEntity(){
        Resource resource = new Resource();
        setEntity(resource);
        return resource;
    }

    public void setEntity(Resource resource){

        resource.setName(name);
        resource.setDescription(description);
        resource.setType(ResourceType.valueOf(type));

        if(properties != null){
            List<Property> list_properties = new ArrayList<>();
            for(PropertyForm propForm:properties){
                Property prop = new Property();
                propForm.setEntity(prop);
                list_properties.add(prop);
            }
            resource.setProperties(list_properties);
        }
        
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
