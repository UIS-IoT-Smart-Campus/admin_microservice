package com.iot.admin.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedisMessage {

    private String type;
    private String queue;
    private String content;
   
}
