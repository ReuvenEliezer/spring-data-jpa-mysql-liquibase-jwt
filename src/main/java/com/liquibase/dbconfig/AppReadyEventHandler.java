package com.liquibase.dbconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.liquibase.services.ServiceApp;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppReadyEventHandler implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    private Map<String, ServiceApp> services = new HashMap<>();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        for (ServiceApp serviceApp : services.values()) {
            serviceApp.start();
        }
    }

}
