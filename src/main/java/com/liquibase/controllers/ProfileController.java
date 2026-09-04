package com.liquibase.controllers;

import com.liquibase.client_entities.CaseViewModel;
import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.repositories.CaseProfileDao;
import com.liquibase.services.web.EntityWebService;
import com.liquibase.services.web.convert.CaseVmConverter;
import com.liquibase.utils.WsAddressConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(WsAddressConstants.profileLogicUrl)
public class ProfileController extends CrudController<ProfileViewModel, Long> {

    private final CaseProfileDao caseProfileDao;

    private final CaseVmConverter caseVmConverter;

    public ProfileController(EntityWebService<ProfileViewModel, Long> webService, CaseProfileDao caseProfileDao, CaseVmConverter caseVmConverter) {
        super(webService);
        this.caseProfileDao = caseProfileDao;
        this.caseVmConverter = caseVmConverter;
    }

    @GetMapping(value = "/getAllCases/{profileId}")
    public List<CaseViewModel> getAllCasesByProfile(@PathVariable Long profileId) {
        return caseVmConverter.convertToVMList(caseProfileDao.findCasesByProfileId(profileId));
    }

    //indicator for profile entity if he related to a case
    @GetMapping(value = "/isRelatedToAnyCase/{profileId}")
    public boolean isRelatedToAnyCase(@PathVariable Long profileId) {
        return caseProfileDao.existsByIdProfileId(profileId);
    }

}
