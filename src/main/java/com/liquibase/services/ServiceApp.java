package com.liquibase.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ServiceApp {

    Logger logger = LogManager.getLogger(ServiceApp.class);

    void start();

}
