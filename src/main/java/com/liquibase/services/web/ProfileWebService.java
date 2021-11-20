package com.liquibase.services.web;

import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.entities.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileWebService extends AbstractEntityWebService<Profile, ProfileViewModel, Long> {

    @Override
    protected void innerDelete(Profile entity) {
        throw new UnsupportedOperationException();
    }

}
