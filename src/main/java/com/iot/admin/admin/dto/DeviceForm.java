package com.iot.admin.admin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Device;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceForm {

    @NotNull(message = "Name can't be null.")
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    private String description;

    private boolean is_gateway;

    private Long device_parent;


    public Device getEntity(){
        Device device = new Device();
        setEntity(device);
        return device;
    }

    public void setEntity(Device device){
        device.setName(name);
        device.setDescription(description);
        device.set_gateway(is_gateway);
        assignRelationships(device);
    }

    /**
     * Sets relationships with other entities.
     * 
     * @param device device entity instance.
     */
    private void assignRelationships(Device device) {
        Device deviceParentEntity = null;

        if (device_parent != null && device_parent != 0)
            deviceParentEntity = new Device(device_parent);
           
        device.setDeviceParent(deviceParentEntity);
    }
}
