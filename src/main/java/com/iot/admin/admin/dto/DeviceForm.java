package com.iot.admin.admin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.DeviceType;
import com.iot.admin.admin.entity.Gateway;
import com.iot.admin.admin.utils.validations.EnumValue;

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
    
    @EnumValue(enumClass = DeviceType.class, message = "Invalid device type")
    private String device_type;

    private Long device_parent;

    private Long gateway;

    public Device getEntity(){
        Device device = new Device();
        setEntity(device);
        return device;
    }

    public void setEntity(Device device){
        device.setTag(tag);
        device.setName(name);
        device.setDescription(description);
        device.setDeviceType(DeviceType.valueOf(device_type));

        assignRelationships(device);
    }

    /**
     * Sets relationships with other entities.
     * 
     * @param device device entity instance.
     */
    private void assignRelationships(Device device) {
        Device deviceParentEntity = null;
        Gateway gatewayEntity = null;

        if (device_parent != null)
            deviceParentEntity = new Device(device_parent);

        if (gateway != null)
            gatewayEntity = new Gateway(gateway);

        device.setDeviceParent(deviceParentEntity);
        device.setGateway(gatewayEntity);
    }
}
