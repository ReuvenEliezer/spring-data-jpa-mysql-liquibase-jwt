package com.liquibase.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ServiceApp {

    Logger logger = LoggerFactory.getLogger(ServiceApp.class);

    void start();

}
