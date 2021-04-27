package com.example.demo.sftp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: fate
 * @description:
 * @date: 2021/4/25  11:27
 **/
@Data
@Component
@ConfigurationProperties(prefix = "storage")
public class SftpProp {

    private String hostName;
    private String serviceType;
    private Integer port;
    private String username;
    private String password;
    private String poolInitCount;
    private String poolMaxCount;
    private String tmpFileCleanTime;
    private String directory;
    private Integer sessionConnectTimeout;
    private Integer channelConnectedTimeout;
    private String sessionStrictHostKeyChecking;
}