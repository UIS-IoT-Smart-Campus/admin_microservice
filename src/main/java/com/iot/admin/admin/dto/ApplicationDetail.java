package com.iot.admin.admin.dto;

import java.util.Date;

import com.iot.admin.admin.entity.Application;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationDetail {

    private long id;
    private long global_id;
    private String name;
    private Date created_at;

    public void setEntity(Application app){
        id = app.getId();
        global_id = app.getGlobal_id();
        name = app.getName();
        created_at = app.getCreate_at();
    }
    
}
