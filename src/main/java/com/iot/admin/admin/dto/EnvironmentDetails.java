package com.iot.admin.admin.dto;

import java.util.ArrayList;
import java.util.List;

import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Environment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnvironmentDetails {

    private long id;
    private String name;
    private String description;

    private List<DeviceListDetails> devices = new ArrayList<>();


    public void setEntity(Environment environment){
        id = environment.getId();
        name = environment.getName();
        description = environment.getDescription();

        if(environment.getDevices() != null){
            for(Device d:environment.getDevices()){
                DeviceListDetails deviceDetail = new DeviceListDetails();
                deviceDetail.setEntity(d);
                devices.add(deviceDetail);
            }
        }
    }    
}
