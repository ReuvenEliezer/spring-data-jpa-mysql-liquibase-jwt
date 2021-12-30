package com.liquibase.services;

import org.springframework.stereotype.Service;

@Service
public class BServiceImpl implements ServiceApp {

    @Override
    public void start() {
        logger.debug("start BServiceImpl");
    }
}
