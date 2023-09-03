package com.liquibase.services.audit;

import com.liquibase.entities.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class AuditTrailListener {

    /**
     * https://www.baeldung.com/jpa-entity-lifecycle-events
     */
    private final Logger logger = LogManager.getLogger(this.getClass());

    @PostPersist
    @PostUpdate
    @PostRemove
    public void afterAnyUpdate(AbstractEntity entity) {
        logger.info("[entity AUDIT] add/update/delete complete for entity: " + entity.getId());
    }
}
