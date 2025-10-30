package io.hexlet.cv.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.inertia4j.spi.PageObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class InertiaConfig {
    @Bean
    @Primary
    public PageObjectSerializer pageObjectSerializer(ObjectMapper objectMapper) {
        return (pageObject, request) -> {
            try {
                return objectMapper.writeValueAsString(pageObject);
            } catch (Exception e) {
                throw new RuntimeException("Serialization failed", e);
            }
        };
    }
}
