package com.liquibase.services;

import org.springframework.stereotype.Service;

@Service
public class BServiceImpl implements ServiceApp {

    @Override
    public void start() {
        System.out.println("start BServiceImpl");
    }
}
