package com.liquibase.services.audit;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuditListener extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        //TODO do logic
        logger.info("beforeRequest");
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        //TODO do logic
        logger.info("afterRequest");
    }
}
