package com.iot.admin.admin.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Resource;
import com.iot.admin.admin.entity.ResourceType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceForm {


    @NotNull(message = "Name can't be null.")
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    private String description;

    @NotNull(message = "Type can't be null.")
    @NotEmpty(message = "Type can't empty.")
    @Enumerated(EnumType.STRING)
    private String resource_type;

    public Resource getEntity(){
        Resource resource = new Resource();
        setEntity(resource);
        return resource;
    }

    public void setEntity(Resource resource){
        resource.setName(name);
        resource.setDescription(description);
        resource.setResource_type(ResourceType.valueOf(resource_type));
    }    
}
