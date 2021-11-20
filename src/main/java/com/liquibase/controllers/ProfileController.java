package com.liquibase.controllers;

import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.utils.WsAddressConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WsAddressConstants.profileLogicUrl)
public class ProfileController extends CrudController<ProfileViewModel, Long> {

}
