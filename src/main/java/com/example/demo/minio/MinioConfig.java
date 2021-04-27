package com.example.demo.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: fate
 * @description:
 * @date: 2021/4/25  11:34
 **/
@Configuration
@EnableConfigurationProperties(MinioProp.class)
public class MinioConfig {

    @Autowired
    private MinioProp minioProp;

    /**
     * 获取MinioClient
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioProp.getEndpoint()).credentials(minioProp.getAccesskey(), minioProp.getSecretkey()).build();
    }

}