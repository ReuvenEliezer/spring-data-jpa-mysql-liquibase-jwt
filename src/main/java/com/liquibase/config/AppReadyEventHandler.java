package com.liquibase.config;

import com.liquibase.services.ServiceApp;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AppReadyEventHandler implements ApplicationListener<ApplicationReadyEvent> {


    private final Map<String, ServiceApp> services;

    public AppReadyEventHandler(Map<String, ServiceApp> services) {
        this.services = services;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        for (ServiceApp serviceApp : services.values()) {
            serviceApp.start();
        }
    }

}
