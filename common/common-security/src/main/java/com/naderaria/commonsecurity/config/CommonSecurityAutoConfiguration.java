package com.naderaria.commonsecurity.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
@ComponentScan(basePackages = "com.naderaria.commonsecurity")
public class CommonSecurityAutoConfiguration {
}
