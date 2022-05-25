package com.iot.admin.admin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Environment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnvironmentForm {

    @NotNull(message = "Name can't be null.")
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    private String description;

    public Environment getEntity(){
        Environment enviroment = new Environment();
        setEntity(enviroment);
        return enviroment;
    }

    public void setEntity(Environment enviroment){
        enviroment.setName(name);
        enviroment.setDescription(description);                
    }
    
}
