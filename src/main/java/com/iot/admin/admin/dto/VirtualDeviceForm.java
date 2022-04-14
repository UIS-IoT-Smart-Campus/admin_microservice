package com.iot.admin.admin.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VirtualDeviceForm {

    @NotNull(message = "Device reference can't be null.")
    private Long deviceReference;

    @Enumerated(EnumType.STRING)
    private String status;    
    
}
