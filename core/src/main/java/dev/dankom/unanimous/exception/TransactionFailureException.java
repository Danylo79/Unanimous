package dev.dankom.unanimous.exception;

public class TransactionFailureException extends Exception {
    public TransactionFailureException(Throwable throwable) {
        super(throwable);
    }
}
