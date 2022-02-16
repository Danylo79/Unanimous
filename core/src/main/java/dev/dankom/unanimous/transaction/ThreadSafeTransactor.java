package dev.dankom.unanimous.transaction;

import dev.dankom.unanimous.exception.InsufficientFundsException;
import dev.dankom.unanimous.exception.TransactionFailureException;
import dev.dankom.unanimous.group.profile.UProfile;
import dev.dankom.unanimous.group.transaction.UTransaction;
import dev.dankom.unanimous.manager.ClassManager;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class ThreadSafeTransactor {
    private Queue<UTransaction> transactQueue = new LinkedBlockingQueue<>();
    private ClassManager classManager;
    private boolean running = false;

    public ThreadSafeTransactor(ClassManager classManager) {
        this.classManager = classManager;
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
                            UProfile senderProfile = classManager.getProfileInGlobal(transaction.getSender());
                            UProfile receiverProfile = classManager.getProfileInGlobal(transaction.getReceiver());
                            if (senderProfile.getBalance() >= transaction.getAmount() || !senderProfile.shouldCheckFunds()) {
                                senderProfile.getParent().addTransaction(transaction);
                                receiverProfile.getParent().addTransaction(transaction);
                                classManager.save();
                            } else {
                                exceptionCaught(transaction, new InsufficientFundsException(senderProfile));
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

    public abstract void exceptionCaught(UTransaction failedTransaction, Throwable throwable);
}
