package com.iot.admin.admin.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Category;
import com.iot.admin.admin.entity.Device;
//import com.iot.admin.admin.entity.DeviceType;
//import com.iot.admin.admin.entity.Gateway;
//import com.iot.admin.admin.utils.validations.EnumValue;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.entity.Resource;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceForm {

    @NotNull(message = "Name can't be null.")
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    private String description;    

    private List<PropertyForm> properties;

    private List<ResourceForm> resources;

    private List<Long> categories;

    /*
    @EnumValue(enumClass = DeviceType.class, message = "Invalid device type")
    private String device_type;*/

    private Long device_parent;

    private String method;


    public Device getEntity(){
        Device device = new Device();
        setEntity(device);
        return device;
    }

    public void setEntity(Device device){
        device.setName(name);
        device.setDescription(description);

        if(properties != null){
            List<Property> list_properties = new ArrayList<>();
            for(PropertyForm propForm:properties){
                Property prop = new Property();
                propForm.setEntity(prop);
                list_properties.add(prop);
            }
            device.setProperties(list_properties);
        }

        if(resources != null){
            List<Resource> list_resources = new ArrayList<>();
            for(ResourceForm resoForm:resources){
                Resource resource = new Resource();
                resoForm.setEntity(resource);
                list_resources.add(resource);
            }
            device.setResources(list_resources);
        }

        if(categories!= null){
            Set<Category> list_categories = new HashSet<Category>();
            for(Long categoryIdForm:categories){
                Category category = new Category();
                category.setId(categoryIdForm);
                list_categories.add(category);
            }
            device.setCategories(list_categories);
        }
       
        /*device.setDeviceType(DeviceType.valueOf(device_type));*/

        assignRelationships(device);
    }

    /**
     * Sets relationships with other entities.
     * 
     * @param device device entity instance.
     */
    private void assignRelationships(Device device) {
        Device deviceParentEntity = null;
        //Gateway gatewayEntity = null;

        if (device_parent != null && device_parent != 0)
            deviceParentEntity = new Device(device_parent);
        /*
        if (gateway != null)
            gatewayEntity = new Gateway(gateway);*/

        device.setDeviceParent(deviceParentEntity);
        /*device.setGateway(gatewayEntity);*/
    }
}
