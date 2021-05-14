package com.iot.admin.admin.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iot.admin.admin.entity.Device;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceDetails {

    private long id;
    private String tag;
    private String name;
    private String description;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("update_at")
    private Date updateAt;

    private long device_parent;

    private long gateway;

    public void setEntity(Device device){
        id = device.getId();
        tag = device.getTag();
        name = device.getName();
        description = device.getDescription();
        createdAt = device.getCreatedAt();
        updateAt = device.getUpdatedAt();
        device_parent = device.getDevice_parent().getId();
        gateway = device.getGateway().getId();        
    }
    
}
