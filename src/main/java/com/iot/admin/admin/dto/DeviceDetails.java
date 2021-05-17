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

    private Long device_parent;

    private Long gateway;

    public void setEntity(Device device){
        id = device.getId();
        tag = device.getTag();
        name = device.getName();
        description = device.getDescription();
        createdAt = device.getCreatedAt();
        updateAt = device.getUpdatedAt();
        if (device.getDevice_parent() != null) {
            device_parent = device.getDevice_parent().getId();
        }
        if (device.getGateway() != null) {
            gateway = device.getGateway().getId();
        }
    }
    
}
