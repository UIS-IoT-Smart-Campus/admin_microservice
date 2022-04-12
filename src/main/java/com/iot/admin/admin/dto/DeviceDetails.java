package com.iot.admin.admin.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iot.admin.admin.entity.Device;
import com.iot.admin.admin.entity.Property;
import com.iot.admin.admin.entity.Resource;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceDetails {

    private long id;
    private String name;
    private String description;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("update_at")
    private Date updateAt;


    private long device_parent;

    private List<PropertyDetails> properties = new ArrayList<>();

    private List<ResourceDetails> resources = new ArrayList<>();

    //private Long gateway;

    public void setEntity(Device device){
        id = device.getId();
        name = device.getName();
        description = device.getDescription();
        createdAt = device.getCreatedAt();
        updateAt = device.getUpdatedAt();

        if(device.getProperties() != null){
            for(Property p:device.getProperties()){
                PropertyDetails propertyDetails = new PropertyDetails();
                propertyDetails.setEntity(p);
                properties.add(propertyDetails);
            }
        }
        
        if(device.getResources() != null){
            for(Resource r:device.getResources()){
                ResourceDetails resourceDetail = new ResourceDetails();
                resourceDetail.setEntity(r);
                resources.add(resourceDetail);
            }
        }

        if (device.getDeviceParent() != null) {
            device_parent = device.getDeviceParent().getId();
        }
        /*if (device.getGateway() != null) {
            gateway = device.getGateway().getId();
        }*/
    }
    
}
