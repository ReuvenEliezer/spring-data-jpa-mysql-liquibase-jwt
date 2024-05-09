package com.liquibase.services.transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
public class TransactionHandlerImpl implements TransactionHandler {

    /**
     * https://stackoverflow.com/questions/3423972/spring-transaction-method-call-by-the-method-within-the-same-class-does-not-wo
     * https://www.the-itconsultant.com/2022/07/17/transaction-from-jdbc-to-spring-transactional-and-transactiontemplate/#:~:text=see%20another%20one!-,2%20Functional%20interface%20Supplier,-%40Service
     * @param supplier
     * @param <T>
     * @return
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T> T runInTransaction(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T runInNewTransaction(Supplier<T> supplier) {
        return supplier.get();
    }
}