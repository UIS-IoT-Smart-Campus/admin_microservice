package com.iot.admin.admin.dto;

import com.iot.admin.admin.entity.VirtualDevice;
import com.iot.admin.admin.entity.VirtualDeviceStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VirtualDeviceDetails {

    private long id;
    private String tag;
    private String name;
    private String description;
    private Boolean isGateway;
    private VirtualDeviceStatus status;
    private String reference;

    public void setEntity(VirtualDevice virtualDevice){
        id = virtualDevice.getId();
        tag = virtualDevice.getTag();
        name = virtualDevice.getName();
        description = virtualDevice.getDescription();
        isGateway = virtualDevice.getIsGateway();
        status = virtualDevice.getStatus();
        reference = virtualDevice.getDeviceReference().getName();
    }
    
}
