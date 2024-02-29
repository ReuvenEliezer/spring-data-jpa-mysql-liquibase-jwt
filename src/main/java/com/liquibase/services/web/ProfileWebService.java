package com.liquibase.services.web;

import com.liquibase.client_entities.ProfileViewModel;
import com.liquibase.entities.Profile;
import com.liquibase.services.transactional.TransactionalOperationsUtil;
import com.liquibase.services.web.convert.EntityVmConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ProfileWebService extends AbstractEntityWebService<Profile, ProfileViewModel, Long> {

    ProfileWebService(EntityVmConverter<Profile, ProfileViewModel> converter,
                      JpaRepository<Profile, Long> jpaRepository,
                      TransactionalOperationsUtil transactionalOperationsUtil) {
        super(converter, jpaRepository, transactionalOperationsUtil);
    }

    @Override
    protected void innerDelete(Profile entity) {
        throw new UnsupportedOperationException();
    }

}
