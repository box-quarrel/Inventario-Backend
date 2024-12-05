package com.hameed.inventario.config;

import com.hameed.inventario.validator.IdMandatoryValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorsConfig {
    @Bean
    public IdMandatoryValidator idMandatoryValidator(HttpServletRequest request) {
        return new IdMandatoryValidator(request);
    }
}
