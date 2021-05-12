package com.iot.admin.admin.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iot.admin.admin.entity.Gateway;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GatewayDetails {

    private long id;
    private String tag;
    private String name;
    private String description;
    private String ip_address;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("update_at")
    private Date updateAt;

    public void setEntity(Gateway gateway){

        id = gateway.getId();
        tag = gateway.getTag();
        name = gateway.getName();
        description = gateway.getDescription();
        ip_address = gateway.getIp_address();
        createdAt = gateway.getCreatedAt();
        updateAt = gateway.getUpdatedAt();
        
    }

    
}
