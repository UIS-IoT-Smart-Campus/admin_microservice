package com.iot.admin.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sets cors configuration for allow origins.
 */
public class Cors {

    /**
     * Default: Any Origin
     * 
     * NOTE: If the service is not fully public or accessible to any other api,
     * the origins to be allowed should be set.
     */
    private String origins = "*";

    @Bean
    public WebMvcConfigurer corsConfigurer() 
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins(origins)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
