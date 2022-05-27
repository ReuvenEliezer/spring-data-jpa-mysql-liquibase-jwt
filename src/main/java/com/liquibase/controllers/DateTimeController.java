package com.liquibase.controllers;

import com.liquibase.client_entities.CaseViewModel;
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

    @GetMapping(value = "/printLocalDate/{localDate}")
    public void printLocalDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        System.out.println("localDate " + localDate);
    }


    @GetMapping(value = "/printLocalDate1/{localDate}")
    public void printLocalDate1(@PathVariable @DateTimeFormat(pattern = "ddMMyyyy") LocalDate localDate) {
        System.out.println("localDate " + localDate);
    }

    @GetMapping(value = "/printLocalDateTime/{localDateTime}")
    public void printLocalDateTime(@PathVariable LocalDateTime localDateTime) {
        System.out.println("localDateTime " + localDateTime);
    }

    @GetMapping(value = "/printLocalDate3/{localDate}")
    public void printLocalDate3(@PathVariable LocalDate localDate) {
        System.out.println("localDate " + localDate);
    }

    @GetMapping(value = "/printDuration/{duration}")
    public void printDuration(@PathVariable Duration duration) {
        System.out.println("duration " + duration);
    }

}
