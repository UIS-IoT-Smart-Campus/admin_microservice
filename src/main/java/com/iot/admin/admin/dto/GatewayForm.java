package com.iot.admin.admin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Gateway;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GatewayForm {

    @NotNull(message = "Tag doesn't be null.")
    @NotEmpty(message = "Tag not empty.")
    private String tag;

    @NotNull(message = "Name doesn't be null.")
    @NotEmpty(message = "Name not empty.")
    private String name;
    
    private String description;

    private String ip_address;


    public Gateway getEntity(){
        Gateway g = new Gateway();
        g.setTag(tag);
        g.setName(name);
        g.setDescription(description);
        g.setIp_address(ip_address);
        return g;
    }

    public void setEntity(Gateway g){
        g.setTag(tag);
        g.setName(name);
        g.setDescription(description);
        g.setIp_address(ip_address);
    }
}
