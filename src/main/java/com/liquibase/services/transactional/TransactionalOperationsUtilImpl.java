package com.liquibase.services.transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalOperationsUtilImpl implements TransactionalOperationsUtil {

    @Transactional
    public <T> T invokeTransactional(TransactionalInvokeAction<T> action) {
        return action.invoke();
    }


}
