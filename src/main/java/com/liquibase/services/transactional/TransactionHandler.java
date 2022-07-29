package com.liquibase.services.transactional;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

public interface TransactionHandler {

    <T> T runInTransaction(Supplier<T> supplier);

    <T> T runInNewTransaction(Supplier<T> supplier);
}
