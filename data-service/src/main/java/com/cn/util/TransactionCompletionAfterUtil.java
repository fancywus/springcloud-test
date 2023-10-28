package com.cn.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class TransactionCompletionAfterUtil {

    public static void doAfterTransaction(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            log.info("TransactionSynchronizationManager发现存在活动事务，开始注册");
            TransactionSynchronizationManager.registerSynchronization(new DoTransactionCompletion(runnable));
        }
    }
}

@Slf4j
class DoTransactionCompletion implements TransactionSynchronization {

    private Runnable runnable;

    public DoTransactionCompletion(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void afterCompletion(int status) {
        if (status == TransactionSynchronization.STATUS_COMMITTED) {
            log.info("DoTransactionCompletion判断事务提交完成，执行传入线程方法");
            this.runnable.run();
        }
    }
}