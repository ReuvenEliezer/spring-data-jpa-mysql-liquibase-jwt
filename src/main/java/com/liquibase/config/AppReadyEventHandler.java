package com.liquibase.config;

import com.liquibase.services.ServiceApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AppReadyEventHandler implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LogManager.getLogger(AppReadyEventHandler.class);

    private final Map<String, ServiceApp> services;
    private final String env;

    public AppReadyEventHandler(Map<String, ServiceApp> services,
                                @Value("${spring.profiles.active}") String env) {
        this.services = services;
        this.env = env;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("Starting service on {} environment", env);
        for (ServiceApp serviceApp : services.values()) {
            serviceApp.start();
        }
    }

}
