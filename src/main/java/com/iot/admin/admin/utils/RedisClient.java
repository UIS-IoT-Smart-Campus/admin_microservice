package com.iot.admin.admin.utils;

import lombok.Data;

@Data
public class RedisClient {

    private String host;
    private int port;
    private String namespace;
    private String queue;

    public RedisClient(String queue){
        this.host = "localhost";
        this.port = 6379;
        this.namespace = "queue";   
    }
    
}
