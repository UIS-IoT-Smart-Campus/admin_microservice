package com.iot.admin.admin.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Environment;
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

    private boolean gateway;

    private List<PropertyForm> properties;

    private List<ResourceForm> resources;

    private List<DeviceForm> devices;

    private Set<Long> categories;

    /*
    @EnumValue(enumClass = DeviceType.class, message = "Invalid device type")
    private String device_type;*/

    private Long device_parent;

    private Long environment;

    public Device getEntity(){
        Device device = new Device();
        setEntity(device);
        return device;
    }

    public void setEntity(Device device){
        device.setName(name);
        device.setDescription(description);
        device.setGateway(gateway);

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
        
        if(devices != null){
            List<Device> list_devices = new ArrayList<>();
            for(DeviceForm deviForm:devices){
                Device sonDevice = new Device();                
                deviForm.setEntity(sonDevice);
                list_devices.add(sonDevice);
            }
            device.setDevices(list_devices);
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
        Environment enviromentEntity = null;

        if (device_parent != null && device_parent != 0)
            deviceParentEntity = new Device(device_parent);
        
        if (environment != null && environment != 0)
        enviromentEntity = new Environment(environment);
    
        device.setDeviceParent(deviceParentEntity);
        device.setEnvironment(enviromentEntity);
    }
}
