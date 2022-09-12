package com.iot.admin.admin.dto;

import java.util.Date;

import com.iot.admin.admin.entity.Device;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceDetails {

    private long id;
    private long global_id;
    private String name;
    private String description;
    private boolean is_gateway;
    private Date created_at;
    private Date update_at;
    private long device_parent;

    //private Long gateway;
    public void setEntity(Device device){
        id = device.getId();
        name = device.getName();
        description = device.getDescription();
        is_gateway = device.is_gateway();
        created_at = device.getCreate_at();
        update_at = device.getUpdate_at();        

        if (device.getDeviceParent() != null) {
            device_parent = device.getDeviceParent().getId();
        }
    }    
}
