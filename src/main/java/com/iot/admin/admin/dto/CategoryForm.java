package com.iot.admin.admin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.iot.admin.admin.entity.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryForm {

    @NotNull(message = "Name can't be null.")
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    private String description;

    public Category getEntity(){
        Category device = new Category();
        setEntity(device);
        return device;
    }

    public void setEntity(Category category){
        category.setName(name);
        category.setDescription(description);        
    }    
    
}
