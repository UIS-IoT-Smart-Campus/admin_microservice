package com.iot.admin.admin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.DeviceType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceForm {

    @NotNull(message = "Tag doesn't be null.")
    @NotEmpty(message = "Tag not empty.")
    private String tag;

    @NotNull(message = "Name doesn't be null.")
    @NotEmpty(message = "Name not empty.")
    private String name;

    private String description;
    
    private DeviceType device_type;

    private Long device_parent;

    private Long gateway;

    public Device getEntity(){
        Device device = new Device();
        device.setTag(tag);
        device.setName(name);
        device.setDescription(description);
        device.setDevice_type(device_type);
        //device.setDevice_parent(device_parent);
        //device.setGateway(gateway);
        return device;
    }

    public void setEntity(Device device){
        device.setTag(tag);
        device.setName(name);
        device.setDescription(description);
        device.setDevice_type(device_type);
    }

    
}
