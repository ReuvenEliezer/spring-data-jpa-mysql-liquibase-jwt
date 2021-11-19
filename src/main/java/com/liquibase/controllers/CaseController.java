package com.liquibase.controllers;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.utils.WsAddressConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WsAddressConstants.caseLogicUrl)
public class CaseController extends CrudController<CaseViewModel, Long> {

}
