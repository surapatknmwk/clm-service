package com.ktb.clmapiauthen.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound=true,value="classpath:application.yml")
@Getter
public class AppConfig {
    
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.port}")
    private String port;

    @Value("${authentication.bypass.username}")
    private String username;

    @Value("${authentication.bypass.password}")
    private String password;

    @Value("${interface.ad.url}")
    private String ADUrl;

    @Value("${interface.ad.domain}")
    private String ADDomain;

    @Value("${interface.ad.context-factory}")
    private String ADContextFactory;

    @Value("${interface.ad.security-authentication}")
    private String ADSecurityAuthen;

    @Value("${interface.ad.base}")
    private String ADBase;

    @Value("${interface.ad.filter}")
    private String ADFilter;

    @Value("${authentication.security.jwt.secret-key}")
    private String secretKey;

    @Value("${authentication.security.jwt.expiration-a}")
    private long expirationA;

    @Value("${authentication.security.jwt.expiration-b}")
    private long expirationB;
    
    @Value("${authentication.security.jwt.expiration-c}")
    private long expirationC;
}

