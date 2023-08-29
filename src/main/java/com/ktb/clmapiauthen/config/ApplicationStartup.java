package com.ktb.clmapiauthen.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{
    
    @Autowired
    private AppConfig appConfig;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("###########################################################################");
        log.info("ApplicationStartup.....!");
        log.info("Context Path : {}",appConfig.getContextPath());
        log.info("Swagger UI : http://localhost:{}{}/swagger-ui/index.html",appConfig.getPort(),appConfig.getContextPath());
    }
}
