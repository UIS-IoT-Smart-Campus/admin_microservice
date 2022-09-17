package com.iot.admin.admin.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import redis.clients.jedis.Jedis;

@Data
public class RedisClient {

    private String host;
    private int port;
    private String namespace;
    private String queue;

    public String toJsonProperty(Object prop){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(prop);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
        
    }

    public RedisClient(){
        this.host = System.getenv("REDIS_HOST");
        this.port = 6379;
        this.namespace = "queue"; 
        this.queue = "register";
    }

    public void put(String type,String queue,String message){
        Jedis jedis = new Jedis(this.host,this.port);
        Map<String, String> json_message = new HashMap<String, String>();
        json_message.put("type", type);
        json_message.put("queue", queue);
        json_message.put("content", message);
        String message_json = toJsonProperty(json_message);
        jedis.rpush(this.namespace+":"+this.queue, message_json);
        jedis.close();
    }
    
}
