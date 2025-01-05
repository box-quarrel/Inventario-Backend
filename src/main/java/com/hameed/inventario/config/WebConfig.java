package com.hameed.inventario.config;

import com.hameed.inventario.filter.RequestLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private RequestLoggingInterceptor requestLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLoggingInterceptor)
                .addPathPatterns("/**")  // Intercept all paths
                .excludePathPatterns(
                        "/openapi/**",
                        "/swagger-ui/**",        // Exclude Swagger UI paths
                        "/v3/api-docs/**",       // Exclude OpenAPI spec paths
                        "/swagger-resources/**", // Exclude Swagger resources
                        "/webjars/**"           // Exclude webjars used by Swagger
                );
    }
}
