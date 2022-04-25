package com.iot.admin.admin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.VirtualDevice;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VirtualDeviceForm {

    @NotNull(message = "Device name can't be null.")
    @NotEmpty(message = "Device name can't be empty.")
    private String name;

    private String description;

    @NotNull(message = "Device reference can't be null.")
    private Long deviceReference;
    
    private Boolean isGateway;


    public VirtualDevice getEntity(){
        VirtualDevice virtualDevice = new VirtualDevice();
        setEntity(virtualDevice);
        return virtualDevice;
    }

    
    public void setEntity(VirtualDevice virtualDevice){

        virtualDevice.setName(name);
        virtualDevice.setDescription(description);        

        Device device = new Device();
        device.setId(deviceReference);
        virtualDevice.setDeviceReference(device);

        virtualDevice.setIsGateway(isGateway);
    }
    
}
