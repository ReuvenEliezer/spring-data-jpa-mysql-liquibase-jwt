package com.liquibase.services.transactional;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class TransactionalOperationsUtilImpl implements TransactionalOperationsUtil {

    @Transactional
    public <T> T invokeTransactional(TransactionalInvokeAction<T> action) {
        return action.invoke();
    }

    @FunctionalInterface
    public interface TransactionalInvokeAction<T> {
        T invoke();
    }

}
