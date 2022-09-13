package com.iot.admin.admin.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iot.admin.admin.entity.ServiceModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceDetail {

    private long id;
    private long global_id;
    private String name;
    private Date register_at;

    private List<ResourceDetails> resources = new ArrayList<>();

    public void setEntity(ServiceModel service_model){
        id = service_model.getId();
        global_id = service_model.getGlobal_id();
        name = service_model.getName();
        register_at = service_model.getRegister_at();

        if(service_model.getResources()!= null){
            service_model.getResources().forEach(resource -> {
                ResourceDetails details = new ResourceDetails();
                details.setEntity(resource);
                resources.add(details);
            });
        }        
    }    
}
