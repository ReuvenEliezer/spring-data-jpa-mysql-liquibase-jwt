package com.liquibase.services.transactional;

public interface TransactionalOperationsUtil {

    <T> T invokeTransactional(TransactionalInvokeAction<T> action);

    @FunctionalInterface
    interface TransactionalInvokeAction<T> {
        T invoke();
    }

}
