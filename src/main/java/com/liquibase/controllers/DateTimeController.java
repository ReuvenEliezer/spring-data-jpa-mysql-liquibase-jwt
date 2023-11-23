package com.liquibase.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("times")
public class DateTimeController {
    //https://www.baeldung.com/spring-date-parameters

    private static final Logger logger = LoggerFactory.getLogger(DateTimeController.class);

    @GetMapping(value = "/printLocalDate/{localDate}")
    public void printLocalDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        logger.info("localDate {}", localDate);
    }


    @GetMapping(value = "/printLocalDate1/{localDate}")
    public void printLocalDate1(@PathVariable @DateTimeFormat(pattern = "ddMMyyyy") LocalDate localDate) {
        logger.info("localDate {}", localDate);
    }

    @GetMapping(value = "/printLocalDateTime/{localDateTime}")
    public void printLocalDateTime(@PathVariable LocalDateTime localDateTime) {
        logger.info("localDateTime {}", localDateTime);
    }

    @GetMapping(value = "/printLocalDate3/{localDate}")
    public void printLocalDate3(@PathVariable LocalDate localDate) {
        logger.info("localDate {}", localDate);
    }

    @GetMapping(value = "/printDuration/{duration}")
    public void printDuration(@PathVariable Duration duration) {
        logger.info("duration {}", duration);
    }

}
