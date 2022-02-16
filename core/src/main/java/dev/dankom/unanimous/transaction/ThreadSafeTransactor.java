package dev.dankom.unanimous.transaction;

import dev.dankom.unanimous.exception.InsufficientFundsException;
import dev.dankom.unanimous.exception.TransactionFailureException;
import dev.dankom.unanimous.group.profile.UProfile;
import dev.dankom.unanimous.group.transaction.UTransaction;
import dev.dankom.unanimous.manager.ClassManager;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ThreadSafeTransactor {
    private Queue<UTransaction> transactQueue = new LinkedBlockingQueue<>();
    private ClassManager classManager;
    private boolean running = false;

    public ThreadSafeTransactor(ClassManager classManager) {
        this.classManager = classManager;
        start();
    }

    public void queueTransaction(UTransaction transaction) {
        transactQueue.add(transaction);
        if (!running) {
            start();
        }
    }

    public void start() {
        running = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    if (!transactQueue.isEmpty()) {
                        System.out.println("Started transaction");
                        UTransaction transaction = transactQueue.remove();
                        try {
                            UProfile sender = classManager.getProfileInGlobal(transaction.getSender());
                            UProfile receiver = classManager.getProfileInGlobal(transaction.getReceiver());
                            if (sender.getBalance() >= transaction.getAmount() || !sender.shouldCheckFunds()) {
                                sender.getParent().addTransaction(transaction);
                                receiver.getParent().addTransaction(transaction);
                                classManager.save();
                            } else {
                                exceptionCaught(transaction, new InsufficientFundsException(sender));
                            }
                        } catch (Exception e) {
                            exceptionCaught(transaction, new TransactionFailureException(e));
                        }
                    }
                } else {
                    cancel();
                }
            }
        }, 0);
    }

    public void stop() {
        running = false;
    }

    public void exceptionCaught(UTransaction transaction, Throwable throwable) {}
}
