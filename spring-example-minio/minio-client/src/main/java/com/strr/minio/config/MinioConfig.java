package com.strr.minio.config;

import com.strr.api.model.OssConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Bean
    @ConfigurationProperties(prefix = "minio")
    public OssConfig ossConfig() {
        return new OssConfig();
    }
}
